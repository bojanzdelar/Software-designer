<script setup lang="ts">
import { ref, computed, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { Auth } from "aws-amplify";
import axios from "@/service/index";
import RequirementForm from "@/components/RequirementForm.vue";
import DeleteDialog from "@/components/DeleteDialog.vue";
import type { Header, Item, ServerOptions } from "vue3-easy-data-table";
import type Requirement from "@/types/Requirement";
import type RequirementType from "@/types/RequirementType";
import type RequirementPriority from "@/types/RequirementPriority";
import type RequirementRisk from "@/types/RequirementRisk";
import type RequirementStatus from "@/types/RequirementStatus";
import type PageRequest from "@/types/PageRequest";
import type PageResponse from "@/types/PageResponse";
import type { AxiosResponse } from "axios";

const route = useRoute();
const router = useRouter();

const rowsItems: number[] = [10, 25, 50, 100];
const themeColor: string = "#0049B0";

const headers: Header[] = [
  { text: "ID", value: "id", sortable: true },
  { text: "Title", value: "title", sortable: true },
  { text: "Description", value: "description", sortable: true },
  { text: "Type", value: "type.title", sortable: true },
  { text: "Priority", value: "priority.title", sortable: true },
  { text: "Stakeholders", value: "stakeholders", sortable: true },
  { text: "Risk", value: "risk.title", sortable: true },
  { text: "Status", value: "status.title", sortable: true },
  { text: "Effort assessment", value: "effortAssessment", sortable: true },
];

const headersWithActions: Header[] = [
  ...headers,
  { text: "", value: "action" },
];

const items = ref<Item[]>([]);
const itemsSelected = ref<Item[]>();

const types = ref<RequirementType[]>([]);
const priorities = ref<RequirementPriority[]>([]);
const risks = ref<RequirementRisk[]>([]);
const statuses = ref<RequirementStatus[]>([]);

const serverItemsLength = ref<number>(0);
const serverOptions = ref<ServerOptions>({
  page: 1,
  rowsPerPage: 10,
});

const loading = ref<boolean>(false);

const search = ref<string>("");

const projectType = ref<string>("READ");

const parameters = computed<PageRequest>(() => {
  const { page, rowsPerPage, sortBy, sortType } = serverOptions.value;

  const result: PageRequest = {
    page: page - 1,
    size: rowsPerPage,
  };
  if (sortBy && sortType) {
    result.sort = `${sortBy},${sortType}`;
  }

  return result;
});

watch(
  route,
  () => {
    if (route.name !== "requirements") return;
    getRequirements();
    getProjectType();
  },
  { deep: true }
);
watch(serverOptions, () => getRequirements(), { deep: true });

const getRequirements = async (): Promise<void> => {
  loading.value = true;

  let response: AxiosResponse<PageResponse<Requirement>> | undefined;
  try {
    const id = route.params.project;
    response = await axios.get(
      `/requirement-service/requirements/project/${id}`,
      {
        params: {
          ...parameters.value,
          search: search.value,
        },
      }
    );
  } catch (error: any) {
    router.push("/");
  }

  if (!response) return;
  const data: PageResponse<Requirement> = response.data;

  serverItemsLength.value = data.totalElements;
  if (serverItemsLength.value) {
    items.value = data.content;
  } else {
    items.value = [];
  }

  loading.value = false;
};

const createRequirement = async (requirement: Requirement): Promise<void> => {
  await axios.post("/requirement-service/requirements", {
    ...requirement,
    project: route.params.project,
  });
  getRequirements();
};

const editRequirement = async (requirement: Requirement): Promise<void> => {
  await axios.put(
    `/requirement-service/requirements/${requirement.id}`,
    requirement
  );
  getRequirements();
};

const deleteRequirements = async (): Promise<void> => {
  if (!itemsSelected.value) return;
  const ids: number[] = itemsSelected.value.map((x) => x.id);
  await axios.delete(`/requirement-service/requirements/${ids}`);
  getRequirements();
};

const getRequirementsOptions = async (): Promise<void> => {
  const response = await Promise.all([
    axios.get("/requirement-service/requirement-types/all"),
    axios.get("/requirement-service/requirement-priorities/all"),
    axios.get("/requirement-service/requirement-risks/all"),
    axios.get("/requirement-service/requirement-statuses/all"),
  ]);

  const data = response.map((x) => x.data);
  [types.value, priorities.value, risks.value, statuses.value] = data;
};

const getProjectType = async (): Promise<void> => {
  const groups = (await Auth.currentSession()).getIdToken().decodePayload()[
    "cognito:groups"
  ];
  const projectTitle = route.params.project;
  const project = groups.find((group: string) =>
    group.startsWith(`PROJECT_${projectTitle}_`)
  );
  const type = project.replace(`PROJECT_${projectTitle}_`, "");
  projectType.value = type;
  itemsSelected.value = type === "WRITE" ? [] : undefined;
};

getRequirements();
getRequirementsOptions();
getProjectType();
</script>

<template>
  <v-text-field
    v-model="search"
    class="mb-3 mx-3"
    density="compact"
    variant="filled"
    label="Search requirements"
    append-inner-icon="mdi-magnify"
    single-line
    hide-details
    @click:append-inner="getRequirements"
  ></v-text-field>
  <div v-if="projectType === 'WRITE'" class="text-right mb-3 mr-6">
    <RequirementForm
      :action="'Create'"
      :icon="'mdi-plus'"
      :types="types"
      :priorities="priorities"
      :risks="risks"
      :statuses="statuses"
      @confirm="createRequirement"
    />
    <DeleteDialog :selection="itemsSelected" @confirm="deleteRequirements" />
  </div>
  <EasyDataTable
    v-model:server-options="serverOptions"
    v-model:items-selected="itemsSelected"
    :headers="projectType === 'WRITE' ? headersWithActions : headers"
    :items="items"
    :server-items-length="serverItemsLength"
    :loading="loading"
    :rows-items="rowsItems"
    :theme-color="themeColor"
    table-class-name="wide-table"
    buttonsPagination
  >
    <template v-if="projectType === 'WRITE'" #item-action="{ id }">
      <div class="text-right pa-4">
        <RequirementForm
          :action="'Edit'"
          :icon="'mdi-pen'"
          :requirement-id="id"
          :types="types"
          :priorities="priorities"
          :risks="risks"
          :statuses="statuses"
          @confirm="editRequirement"
        />
      </div>
    </template>
  </EasyDataTable>
</template>
