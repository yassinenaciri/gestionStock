import { IArticleProjet } from 'app/shared/model/article-projet.model';

export interface IPaiement {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  mail?: string | null;
  item?: IArticleProjet | null;
}

export const defaultValue: Readonly<IPaiement> = {};
