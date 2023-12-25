import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

import { Amplify } from "aws-amplify";
import "@aws-amplify/ui-vue/styles.css";

import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "@mdi/font/css/materialdesignicons.css";

import Vue3EasyDataTable from "vue3-easy-data-table";
import "vue3-easy-data-table/dist/style.css";

import "./assets/styles/global.css";
import "./assets/styles/amplify.css";

Amplify.configure({
  Auth: {
    userPoolId: import.meta.env.VITE_COGNITO_USER_POOL_ID,
    userPoolWebClientId: import.meta.env.VITE_COGNITO_USER_POOL_WEB_CLIENT_ID,
  },
});

const vuetify = createVuetify({
  components,
  directives,
  theme: {
    defaultTheme: "myCustomLightTheme",
    themes: {
      myCustomLightTheme: {
        dark: false,
        colors: {
          primary: "#3f51b5",
        },
      },
    },
  },
});

const app = createApp(App).use(router).use(vuetify);
app.component("EasyDataTable", Vue3EasyDataTable);
app.mount("#app");
