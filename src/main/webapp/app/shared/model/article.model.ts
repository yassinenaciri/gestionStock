import { IFournisseurArticle } from 'app/shared/model/fournisseur-article.model';
import { IProjet } from 'app/shared/model/projet.model';

export interface IArticle {
  id?: number;
  nom?: string | null;
  desciption?: string | null;
  optionsAchats?: IFournisseurArticle[] | null;
  projets?: IProjet[] | null;
}

export const defaultValue: Readonly<IArticle> = {};
