export interface IBook {
  id?: number;
  isbn?: string;
  pageCount?: number;
  processed?: boolean;
}

export class Book implements IBook {
  constructor(public id?: number, public isbn?: string, public pageCount?: number, public processed?: boolean) {
    this.processed = this.processed || false;
  }
}
