import axios, { type AxiosInstance } from "axios";
import { Auth } from "aws-amplify";

const axiosInstance: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_GATEWAY_URL,
});

const addAuthorizationHeader = async (req: any) => {
  const session = await Auth.currentSession(); // This will refresh the tokens if expired
  req.headers.Authorization = `Bearer ${session
    .getAccessToken()
    .getJwtToken()}`;
  return req;
};

axiosInstance.interceptors.request.use(addAuthorizationHeader);

export default axiosInstance;
