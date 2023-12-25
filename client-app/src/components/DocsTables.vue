<script setup lang="ts">
import { ref, watch } from "vue";
import { useRoute } from "vue-router";
import ReadOnlyTable from "@/components/ReadOnlyTable.vue";
import meta from "@/assets/meta.json";
import type { Item } from "vue3-easy-data-table";

const route = useRoute();

const tab = ref<string>();

const selectedType = ref<any>();
const selectedItem = ref<Item>();

const project = ref<string>(route.params.project as string);
const projectDataSource = ref<string>("");

watch(
  route,
  () => {
    if (route.name !== "docs") return;
    initialize();
  },
  { deep: true }
);

const initialize = () => {
  const type = route.params.type;
  selectedType.value = meta.find((m: any) => m.key === type);

  project.value = route.params.project as string;
  projectDataSource.value = selectedType.value.dataSource + project.value;

  selectedItem.value = undefined;
};

const mainTableRowClicked = (item: any) => {
  selectedItem.value = item;
};

initialize();
</script>

<template>
  <v-tabs bg-color="primary">
    <v-tab>{{ selectedType.name }}</v-tab>
  </v-tabs>
  <ReadOnlyTable
    :headers="selectedType.headers"
    :dataSource="projectDataSource"
    @rowClicked="mainTableRowClicked"
  />

  <template v-if="selectedItem && selectedType.children">
    <v-tabs v-model="tab" bg-color="primary">
      <v-tab
        v-for="child in selectedType.children"
        :key="child.key"
        :value="child.key"
      >
        {{ child.name }}
      </v-tab>
    </v-tabs>
    <v-window v-model="tab">
      <v-window-item
        v-for="child in selectedType.children"
        :key="child.key"
        :value="child.key"
      >
        <ReadOnlyTable
          :headers="meta.find((m: any) => m.key === child.key)?.headers"
          :data-source="child.dataSource + selectedItem.id"
        />
      </v-window-item>
    </v-window>
  </template>
</template>
