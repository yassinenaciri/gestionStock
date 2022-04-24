import { IFrais } from 'app/shared/model/frais.model';
import { IArticleProjet } from 'app/shared/model/article-projet.model';
import { IArticle } from 'app/shared/model/article.model';
import { IResponsableProjet } from 'app/shared/model/responsable-projet.model';

export interface IProjet {
  id?: number;
  nom?: string | null;
  description?: string | null;
  fraisSupplementaires?: IFrais[] | null;
  items?: IArticleProjet[] | null;
  articles?: IArticle[] | null;
  responsables?: IResponsableProjet[] | null;
}

export const defaultValue: Readonly<IProjet> = {};
