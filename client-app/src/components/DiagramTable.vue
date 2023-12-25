<script setup lang="ts">
import { ref, computed, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { Auth } from "aws-amplify";
import axios from "@/service/index";
import DiagramForm from "@/components/DiagramForm.vue";
import DeleteDialog from "@/components/DeleteDialog.vue";
import type { Header, Item, ServerOptions } from "vue3-easy-data-table";
import type Diagram from "@/types/Diagram";
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
  { text: "", value: "action" },
];

const items = ref<Item[]>([]);
const itemsSelected = ref<Item[]>();

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
    if (route.name !== "diagrams") return;
    getDiagrams();
    getProjectType();
  },
  { deep: true }
);
watch(serverOptions, () => getDiagrams(), { deep: true });

const getDiagrams = async (): Promise<void> => {
  loading.value = true;

  let response: AxiosResponse<PageResponse<Diagram>> | undefined;
  try {
    const id = route.params.project;
    response = await axios.get(`/diagram-service/diagrams/project/${id}`, {
      params: {
        ...parameters.value,
        search: search.value,
      },
    });
  } catch (error: any) {
    router.push("/");
  }

  if (!response) return;
  const data: PageResponse<Diagram> = response?.data;

  serverItemsLength.value = data.totalElements;
  if (serverItemsLength.value) {
    items.value = data.content;
  } else {
    items.value = [];
  }

  loading.value = false;
};

const openDiagram = (diagramId: number): void => {
  router.push(`/design/${diagramId}/${projectType.value.toLowerCase()}`);
};

const createDiagram = async (diagram: Diagram): Promise<void> => {
  await axios.post("/diagram-service/diagrams", {
    ...diagram,
    project: route.params.project,
  });
  getDiagrams();
};

const editDiagram = async (diagram: Diagram): Promise<void> => {
  await axios.put(`/diagram-service/diagrams/${diagram.id}`, diagram);
  getDiagrams();
};

const deleteDiagrams = async (): Promise<void> => {
  if (!itemsSelected.value) return;
  const ids: number[] = itemsSelected.value.map((x) => x.id);
  await axios.delete(`/diagram-service/diagrams/${ids}`);
  getDiagrams();
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

const generateCode = async (diagramId: number): Promise<void> => {
  try {
    const diagram = items.value.find((x) => x.id === diagramId);
    if (!diagram) {
      throw new Error("Internal error - Diagram not found");
    }

    const response = await axios.post(
      "/generator-service/generate-code",
      {
        id: diagram.id,
        title: diagram.title,
        data: diagram.data,
      },
      { responseType: "blob" }
    );

    // create link to download file and click it
    const href = URL.createObjectURL(response.data);
    const link = document.createElement("a");
    link.setAttribute("download", `${diagram.title}.zip`);
    link.href = href;
    document.body.appendChild(link);
    link.click();

    // clean up
    document.body.removeChild(link);
    URL.revokeObjectURL(href);
  } catch (error: any) {
    if (error.response && error.response.status === 400) {
      window.alert("Code generation is not supported for this diagram.");
    } else {
      window.alert("Code generator is not available at the moment.");
    }
  }
};

getDiagrams();
getProjectType();
</script>

<template>
  <v-text-field
    v-model="search"
    class="mb-3 mx-3"
    density="compact"
    variant="filled"
    label="Search diagrams"
    append-inner-icon="mdi-magnify"
    single-line
    hide-details
    @click:append-inner="getDiagrams"
  ></v-text-field>
  <div v-if="projectType === 'WRITE'" class="text-right mb-3 mr-6">
    <DiagramForm
      :action="'Create'"
      :icon="'mdi-plus'"
      @confirm="createDiagram"
    />
    <DeleteDialog :selection="itemsSelected" @confirm="deleteDiagrams" />
  </div>
  <EasyDataTable
    v-model:server-options="serverOptions"
    v-model:items-selected="itemsSelected"
    :headers="headers"
    :items="items"
    :server-items-length="serverItemsLength"
    :loading="loading"
    :rows-items="rowsItems"
    :theme-color="themeColor"
    table-class-name="wide-table"
    buttonsPagination
  >
    <template #item-action="{ id }">
      <div class="text-right pa-4">
        <DiagramForm
          v-if="projectType === 'WRITE'"
          :action="'Edit'"
          :icon="'mdi-pen'"
          :diagram-id="id"
          class="mx-1"
          @confirm="editDiagram"
        />
        <v-icon
          icon="mdi-pencil-ruler"
          size="x-large"
          class="mx-1"
          @click="openDiagram(id)"
        ></v-icon>
        <v-icon
          icon="mdi-file-code-outline"
          size="x-large"
          class="mx-1"
          @click="generateCode(id)"
        ></v-icon>
      </div>
    </template>
  </EasyDataTable>
</template>
