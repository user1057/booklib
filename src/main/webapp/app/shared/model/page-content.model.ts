export interface IPageContent {
  id?: number;
  isbn?: string;
  page?: number;
  dataContentType?: string;
  data?: any;
}

export class PageContent implements IPageContent {
  constructor(public id?: number, public isbn?: string, public page?: number, public dataContentType?: string, public data?: any) {}
}
