<script setup lang="ts">
import { useRoute, RouterView } from "vue-router";
import { Authenticator, useAuthenticator } from "@aws-amplify/ui-vue";
import MenuComponent from "@/components/MenuComponent.vue";

const route = useRoute();
const auth = useAuthenticator();
</script>

<template>
  <authenticator
    :login-mechanisms="['email']"
    :hide-sign-up="true"
    class="h-100"
  />
  <MenuComponent
    v-if="auth.route === 'authenticated'"
    class="pa-6 flex flex-col min-h-screen"
  >
    <p v-if="route.path === '/'">Open a project to get started</p>
    <RouterView />
    <div class="grow"></div>
  </MenuComponent>
</template>

<style scoped>
.min-h-screen {
  min-height: 100vh;
}
.flex {
  display: flex;
}

.flex-col {
  flex-direction: column;
}
.grow {
  flex-grow: 1;
}
</style>
