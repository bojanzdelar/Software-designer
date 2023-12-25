<script setup lang="ts">
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { Auth } from "aws-amplify";
import axios from "@/service/index";
import type Diagram from "@/types/Diagram.js";
import type DrawioRequest from "@/types/DrawioRequest.js";
import type DrawioMessage from "@/types/DrawioMessage.js";
import type { AxiosResponse } from "axios";

const route = useRoute();
const router = useRouter();

const urls = {
  write:
    "https://embed.diagrams.net?embed=1&configure=1&ui=atlas&spin=1&modified=unsavedChanges&proto=json",
  read: "https://embed.diagrams.net?embed=1&configure=1&ui=atlas&spin=1&modified=unsavedChanges&proto=json&saveAndExit=0&noSaveBtn=1",
};
const url = ref<string>(urls.read);

const iframe = ref<HTMLIFrameElement>();
const diagram = ref<Diagram>();

const configureDrawio = (): void => {
  iframe.value?.contentWindow?.postMessage(
    JSON.stringify({ action: "configure", config: { compressXml: true } }),
    "*"
  );
};

const getProjectType = async (): Promise<void> => {
  const type = route.params.type;
  if (type !== "write" && type !== "read") {
    router.go(-1);
    return;
  }

  url.value = urls[type];
};

const validateProjectType = async (projectTitle: string): Promise<void> => {
  const groups = (await Auth.currentSession()).getIdToken().decodePayload()[
    "cognito:groups"
  ];
  const type = route.params.type;
  const hasWritePrivilege = groups.includes(`PROJECT_${projectTitle}_WRITE`);
  if (type === "write" && !hasWritePrivilege) {
    router.go(-1);
    return;
  }
};

const getDiagram = async (): Promise<void> => {
  let response: AxiosResponse;
  try {
    response = await axios.get(`/diagram-service/diagrams/${route.params.id}`);
  } catch (err) {
    router.go(-1);
    return;
  }

  diagram.value = response.data[0];
};

const setDiagram = async (): Promise<void> => {
  if (!diagram.value) {
    return;
  }

  const request: DrawioRequest = { action: "load", xml: diagram.value.data };
  iframe.value?.contentWindow?.postMessage(JSON.stringify(request), "*");
};

const saveDiagram = (data: string): void => {
  if (!diagram.value) {
    return;
  }

  diagram.value.data = data;
  axios.put(`/diagram-service/diagrams/${route.params.id}`, diagram.value);
};

window.addEventListener(
  "message",
  async (messageEvent: MessageEvent): Promise<void> => {
    if (messageEvent.origin !== "https://embed.diagrams.net") {
      return;
    }

    const { event: eventType, xml: data }: DrawioMessage = JSON.parse(
      messageEvent.data
    );

    if (eventType === "configure") {
      configureDrawio();
    } else if (eventType === "init") {
      await validateProjectType(diagram.value?.project || "");
      setDiagram();
    } else if (eventType === "save" && data) {
      saveDiagram(data);
    } else if (eventType === "exit") {
      router.go(-1);
    }
  }
);

getProjectType();
getDiagram();
</script>

<template>
  <iframe ref="iframe" :src="url" title="draw.io" class="w-100 h-100"></iframe>
</template>

<style scoped>
iframe {
  border-width: 0;
}
</style>
