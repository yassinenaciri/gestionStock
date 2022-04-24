import { IEntreprise } from 'app/shared/model/entreprise.model';

export interface IAdmin {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  entreprises?: IEntreprise | null;
}

export const defaultValue: Readonly<IAdmin> = {};
