import { IFournisseurArticle } from 'app/shared/model/fournisseur-article.model';

export interface IFournisseur {
  id?: number;
  nom?: string | null;
  ice?: string | null;
  tel?: string | null;
  adresse?: string | null;
  description?: string | null;
  menus?: IFournisseurArticle[] | null;
}

export const defaultValue: Readonly<IFournisseur> = {};
