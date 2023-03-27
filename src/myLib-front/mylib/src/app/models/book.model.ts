import { Author } from "./author.model";
import { Collection } from "./collection.model";
import { Editor } from "./editor.model";
import { Illustrator } from "./illustrator.model";

export class Book {
    id!: number;
    title!: string;
    author!: Author;
    illustrator!: Illustrator;
    editor!: Editor;
    collection!: Collection;
}