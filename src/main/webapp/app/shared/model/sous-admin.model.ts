import { IEntreprise } from 'app/shared/model/entreprise.model';

export interface ISousAdmin {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  mail?: string | null;
  entreprises?: IEntreprise | null;
}

export const defaultValue: Readonly<ISousAdmin> = {};
