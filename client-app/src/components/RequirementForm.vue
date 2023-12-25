<script setup lang="ts">
import { ref, watch } from "vue";
import axios from "@/service/index";
import type { AxiosResponse } from "axios";
import type Requirement from "@/types/Requirement";
import type RequirementType from "@/types/RequirementType";
import type RequirementPriority from "@/types/RequirementPriority";
import type RequirementRisk from "@/types/RequirementRisk";
import type RequirementStatus from "@/types/RequirementStatus";

const propsForm = defineProps<{
  action: string;
  icon: string;
  requirementId?: number;
  types: RequirementType[];
  priorities: RequirementPriority[];
  risks: RequirementRisk[];
  statuses: RequirementStatus[];
}>();

const emit = defineEmits<{ (e: "confirm", requirement: Requirement): void }>();

const dialog = ref<boolean>(false);

const rules = [
  (v: any) =>
    v === undefined || v === null || (v.length && v.length === 0)
      ? "This field is required"
      : true,
];

const requirement = ref<Requirement>({});

watch(
  () => dialog.value,
  () => {
    if (dialog.value && propsForm.requirementId) {
      getRequirement(propsForm.requirementId);
    }
  }
);

const getRequirement = async (id: number): Promise<void> => {
  let response: AxiosResponse<Requirement[]>;
  try {
    response = await axios.get(`/requirement-service/requirements/${id}`);
    requirement.value = response.data[0];
  } catch (error: any) {
    close();
  }
};

const submit = async (event: SubmitEvent): Promise<void> => {
  const results: any = await event;
  if (!results.valid) {
    return;
  }

  emit("confirm", { ...requirement.value });
  close();
};

const close = (): void => {
  requirement.value = {};
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
        <v-card-title>{{ propsForm.action }} requirement</v-card-title>

        <v-card-text>
          <v-text-field
            v-model="requirement.title"
            :rules="rules"
            label="Title"
            required
          ></v-text-field>

          <v-textarea
            v-model="requirement.description"
            :rules="rules"
            label="Description"
            required
          ></v-textarea>

          <v-select
            v-model="requirement.type"
            :rules="rules"
            :items="propsForm.types"
            label="Type"
            return-object
            required
          ></v-select>

          <v-select
            v-model="requirement.priority"
            :rules="rules"
            :items="propsForm.priorities"
            label="Priority"
            return-object
            required
          ></v-select>

          <v-text-field
            v-model="requirement.stakeholders"
            :rules="rules"
            label="Stakeholders"
            required
          ></v-text-field>

          <v-select
            v-model="requirement.risk"
            :rules="rules"
            :items="propsForm.risks"
            label="Risk"
            return-object
            required
          ></v-select>

          <v-select
            v-model="requirement.status"
            :rules="rules"
            :items="propsForm.statuses"
            label="Status"
            return-object
            required
          ></v-select>

          <v-text-field
            v-model="requirement.effortAssessment"
            :rules="rules"
            label="Effort assessment"
            required
          ></v-text-field>

          <v-card-actions>
            <v-btn type="submit" color="primary">Submit</v-btn>
            <v-btn color="primary" @click="close">Cancel</v-btn>
          </v-card-actions>
        </v-card-text>
      </v-card>
    </v-form>
  </v-dialog>
</template>
