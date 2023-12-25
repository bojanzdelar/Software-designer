<script setup lang="ts">
import { ref, computed, watch } from "vue";
import { useRouter } from "vue-router";
import axios from "@/service/index";
import type { Header, Item, ServerOptions } from "vue3-easy-data-table";
import type PageRequest from "@/types/PageRequest";
import type PageResponse from "@/types/PageResponse";
import type { AxiosResponse } from "axios";

const props = defineProps<{
  headers?: Header[];
  dataSource: string;
}>();

const emits = defineEmits<{
  (e: "rowClicked", item: Item): void;
}>();

const router = useRouter();

const rowsItems: number[] = [10];
const themeColor: string = "#0049B0";

const items = ref<Item[]>([]);
const itemsSelected = ref<Item[]>();

const serverItemsLength = ref<number>(0);
const serverOptions = ref<ServerOptions>({
  page: 1,
  rowsPerPage: 10,
});

const loading = ref<boolean>(false);

const search = ref<string>("");

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
  () => props.dataSource,
  () => {
    initialize();
  },
  { deep: true }
);

watch(
  serverOptions,
  () => {
    getData();
  },
  { deep: true }
);

const getData = async (): Promise<void> => {
  loading.value = true;

  let response: AxiosResponse<PageResponse> | undefined;
  try {
    response = await axios.get(`/diagram-service/${props.dataSource}`, {
      params: {
        ...parameters.value,
        search: search.value,
      },
    });
  } catch (error: any) {
    router.push("/");
  }

  if (!response) return;
  const data: PageResponse = response.data;

  serverItemsLength.value = data.totalElements;
  if (serverItemsLength.value) {
    items.value = data.content;
  } else {
    items.value = [];
  }

  loading.value = false;
};

const initialize = () => {
  // this will always trigger the watch on serverOptions
  serverOptions.value = {
    page: 1,
    rowsPerPage: 10,
  };
  search.value = "";
};

getData();
</script>

<template>
  <v-text-field
    v-model="search"
    class="ma-3"
    density="compact"
    variant="filled"
    label="Search"
    append-inner-icon="mdi-magnify"
    single-line
    hide-details
    @click:append-inner="getData"
  ></v-text-field>

  <EasyDataTable
    v-model:server-options="serverOptions"
    v-model:items-selected="itemsSelected"
    :headers="headers"
    :items="items"
    :server-items-length="serverItemsLength"
    :loading="loading"
    :rows-items="rowsItems"
    :theme-color="themeColor"
    buttonsPagination
    @click-row="emits('rowClicked', $event)"
  />
</template>
