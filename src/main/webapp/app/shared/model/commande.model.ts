import { IFournisseurArticle } from 'app/shared/model/fournisseur-article.model';

export interface ICommande {
  id?: number;
  nom?: string | null;
  description?: string | null;
  recu?: boolean | null;
  quantite?: number | null;
  items?: IFournisseurArticle[] | null;
}

export const defaultValue: Readonly<ICommande> = {
  recu: false,
};
