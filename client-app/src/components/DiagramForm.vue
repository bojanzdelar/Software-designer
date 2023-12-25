<script setup lang="ts">
import { ref, watch } from "vue";
import axios from "@/service/index";
import type { AxiosResponse } from "axios";
import type Diagram from "@/types/Diagram";

const propsForm = defineProps<{
  action: string;
  icon: string;
  diagramId?: number;
}>();

const emit = defineEmits<{ (e: "confirm", diagram: Diagram): void }>();

const dialog = ref<boolean>(false);

const rules = [
  (v: any) =>
    v === undefined || v === null || (v.length && v.length === 0)
      ? "This field is required"
      : true,
];

const diagram = ref<Diagram>({});

watch(
  () => dialog.value,
  () => {
    if (dialog.value && propsForm.diagramId) {
      getDiagram(propsForm.diagramId);
    }
  }
);

const getDiagram = async (id: number): Promise<void> => {
  let response: AxiosResponse<Diagram[]>;
  try {
    response = await axios.get(`/diagram-service/diagrams/${id}`);
    diagram.value = response.data[0];
  } catch (error: any) {
    close();
  }
};

const submit = async (event: SubmitEvent): Promise<void> => {
  const results: any = await event;
  if (!results.valid) {
    return;
  }

  emit("confirm", { ...diagram.value });
  close();
};

const close = (): void => {
  diagram.value = {};
  dialog.value = false;
};
</script>

<template>
  <v-dialog v-model="dialog" @keydown.esc="close" width="600">
    <template v-slot:activator="{ props }">
      <v-icon v-bind="props" :icon="propsForm.icon" size="x-large"></v-icon>
    </template>

    <v-form validate-on="submit" @submit.prevent="submit">
      <v-card>
        <v-card-title>{{ propsForm.action }} diagram</v-card-title>

        <v-card-text>
          <v-text-field
            v-model="diagram.title"
            :rules="rules"
            label="Title"
            required
          ></v-text-field>

          <v-textarea
            v-model="diagram.description"
            :rules="rules"
            label="Description"
            required
          ></v-textarea>
        </v-card-text>

        <v-card-actions>
          <v-btn type="submit" color="primary">Submit</v-btn>
          <v-btn color="primary" @click="close">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-form>
  </v-dialog>
</template>
