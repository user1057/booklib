export interface IBookContent {
  id?: number;
  dataContentType?: string;
  data?: any;
  bookContentId?: number;
}

export class BookContent implements IBookContent {
  constructor(public id?: number, public dataContentType?: string, public data?: any, public bookContentId?: number) {}
}
