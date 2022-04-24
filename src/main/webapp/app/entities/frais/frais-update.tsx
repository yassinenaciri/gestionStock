import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjet } from 'app/shared/model/projet.model';
import { getEntities as getProjets } from 'app/entities/projet/projet.reducer';
import { IFrais } from 'app/shared/model/frais.model';
import { getEntity, updateEntity, createEntity, reset } from './frais.reducer';

export const FraisUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const projets = useAppSelector(state => state.projet.entities);
  const fraisEntity = useAppSelector(state => state.frais.entity);
  const loading = useAppSelector(state => state.frais.loading);
  const updating = useAppSelector(state => state.frais.updating);
  const updateSuccess = useAppSelector(state => state.frais.updateSuccess);
  const handleClose = () => {
    props.history.push('/frais');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProjets({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fraisEntity,
      ...values,
      projet: projets.find(it => it.id.toString() === values.projet.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...fraisEntity,
          projet: fraisEntity?.projet?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.frais.home.createOrEditLabel" data-cy="FraisCreateUpdateHeading">
            Create or edit a Frais
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="frais-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Prix" id="frais-prix" name="prix" data-cy="prix" type="text" />
              <ValidatedField label="Desscription" id="frais-desscription" name="desscription" data-cy="desscription" type="text" />
              <ValidatedField id="frais-projet" name="projet" data-cy="projet" label="Projet" type="select">
                <option value="" key="0" />
                {projets
                  ? projets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/frais" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FraisUpdate;
