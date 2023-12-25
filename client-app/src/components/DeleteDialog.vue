<script setup lang="ts">
import { ref } from "vue";
import type { Item } from "vue3-easy-data-table";

const props = defineProps<{
  selection?: Item[];
}>();

const emit = defineEmits<{ (e: "confirm"): void }>();

const dialog = ref<boolean>(false);

const checkSelection = (): void => {
  if (!props.selection || !props.selection.length) {
    close();
  }
};

const confirm = (): void => {
  emit("confirm");
  close();
};

const close = (): void => {
  dialog.value = false;
};
</script>

<template>
  <v-dialog v-model="dialog" @keydown.esc="close" width="400">
    <template v-slot:activator="{ props }">
      <v-icon
        icon="mdi-delete"
        size="x-large"
        v-bind="props"
        @click="checkSelection"
      ></v-icon>
    </template>

    <v-card>
      <v-card-title>Confirm deletion</v-card-title>
      <v-card-text>
        Are you sure you want to delete the selected items?
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" @click="close">Cancel</v-btn>
        <v-btn color="red" @click="confirm">Confirm</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
