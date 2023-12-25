<script setup lang="ts">
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { Auth } from "aws-amplify";
import meta from "@/assets/meta.json";
import type Project from "@/types/Project";

const route = useRoute();
const router = useRouter();

const title = ref<string>("Software designer");
const drawer = ref<boolean>(false);
const projects = ref<Array<Project>>();

watch(
  route,
  () => {
    setTitle();
  },
  { deep: true }
);

const getProjects = async (): Promise<void> => {
  const groups = (await Auth.currentSession()).getIdToken().decodePayload()[
    "cognito:groups"
  ];

  const projectsMapped = groups
    .filter((group: string) => group.startsWith("PROJECT_"))
    .map((group: string) => group.replace("PROJECT_", ""));

  const projectsWrite = projectsMapped
    .filter((group: string) => group.endsWith("_WRITE"))
    .map((group: string) => group.replace("_WRITE", ""))
    .map((group: string) => ({ title: group, type: "WRITE" }));

  const projectsRead = projectsMapped
    .filter((group: string) => group.endsWith("_READ"))
    .map((group: string) => group.replace("_READ", ""))
    .map((group: string) => ({ title: group, type: "READ" }));

  projects.value = projectsWrite.concat(projectsRead);
};

const openProject = (project: Project, data: string): void => {
  router.push(`/${project.title}/${data}`);
  setTitle();
  drawer.value = false;
};

const setTitle = (): void => {
  if (Object.keys(route.params).length) {
    const projectTitle = route.params.project;
    const data = route.path.split("/").slice(-1);
    title.value = `${projectTitle}  - ${data}`;
  } else {
    title.value = "Software designer";
  }
};

const signOut = async (): Promise<void> => {
  await Auth.signOut();
  router.push("/");
};

getProjects();
setTitle();
</script>

<template>
  <v-card>
    <v-layout>
      <v-app-bar color="primary" prominent>
        <v-app-bar-nav-icon
          @click.stop="drawer = !drawer"
          variant="text"
          size="x-large"
        />
        <v-toolbar-title>
          {{ title }}
        </v-toolbar-title>
        <v-spacer />
        <v-btn
          @click="signOut"
          variant="text"
          icon="mdi-exit-to-app"
          size="x-large"
        />
      </v-app-bar>

      <v-navigation-drawer v-model="drawer" location="left" temporary>
        <v-list>
          <template v-for="project in projects" :key="project">
            <v-list-subheader>
              {{ project.title }} ({{ project.type }})
            </v-list-subheader>
            <v-list-item @click="openProject(project, 'requirements')">
              <v-list-item-title>Requirements</v-list-item-title>
            </v-list-item>
            <v-list-item @click="openProject(project, 'diagrams')">
              <v-list-item-title>Diagrams</v-list-item-title>
            </v-list-item>
            <v-list-item
              v-for="m in meta"
              :key="m.key"
              @click="openProject(project, `docs/${m.key}`)"
            >
              <v-list-item-title>{{ m.name }}</v-list-item-title>
            </v-list-item>
            <v-divider />
          </template>
        </v-list>
      </v-navigation-drawer>

      <v-main>
        <slot></slot>
      </v-main>
    </v-layout>
  </v-card>
</template>
