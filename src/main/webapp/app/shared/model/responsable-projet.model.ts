import { IProjet } from 'app/shared/model/projet.model';

export interface IResponsableProjet {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  mail?: string | null;
  projets?: IProjet[] | null;
}

export const defaultValue: Readonly<IResponsableProjet> = {};
