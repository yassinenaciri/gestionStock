import { IProjet } from 'app/shared/model/projet.model';

export interface IFrais {
  id?: number;
  prix?: number | null;
  desscription?: string | null;
  projet?: IProjet | null;
}

export const defaultValue: Readonly<IFrais> = {};
