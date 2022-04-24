import { IArticle } from 'app/shared/model/article.model';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { ICommande } from 'app/shared/model/commande.model';

export interface IFournisseurArticle {
  id?: number;
  prix?: number | null;
  article?: IArticle | null;
  fournisseur?: IFournisseur | null;
  commandes?: ICommande[] | null;
}

export const defaultValue: Readonly<IFournisseurArticle> = {};
