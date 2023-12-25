export default interface PageResponse<T = any> {
  content: Array<T>;
  totalElements: number;
}
