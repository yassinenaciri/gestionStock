import { IArticleProjet } from 'app/shared/model/article-projet.model';

export interface IClient {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  mail?: string | null;
  articles?: IArticleProjet[] | null;
}

export const defaultValue: Readonly<IClient> = {};
