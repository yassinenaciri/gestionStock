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
import { IResponsableProjet } from 'app/shared/model/responsable-projet.model';
import { getEntity, updateEntity, createEntity, reset } from './responsable-projet.reducer';

export const ResponsableProjetUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const projets = useAppSelector(state => state.projet.entities);
  const responsableProjetEntity = useAppSelector(state => state.responsableProjet.entity);
  const loading = useAppSelector(state => state.responsableProjet.loading);
  const updating = useAppSelector(state => state.responsableProjet.updating);
  const updateSuccess = useAppSelector(state => state.responsableProjet.updateSuccess);
  const handleClose = () => {
    props.history.push('/responsable-projet');
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
      ...responsableProjetEntity,
      ...values,
      projets: mapIdList(values.projets),
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
          ...responsableProjetEntity,
          projets: responsableProjetEntity?.projets?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.responsableProjet.home.createOrEditLabel" data-cy="ResponsableProjetCreateUpdateHeading">
            Create or edit a ResponsableProjet
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="responsable-projet-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Nom" id="responsable-projet-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Prenom" id="responsable-projet-prenom" name="prenom" data-cy="prenom" type="text" />
              <ValidatedField label="Mail" id="responsable-projet-mail" name="mail" data-cy="mail" type="text" />
              <ValidatedField label="Projets" id="responsable-projet-projets" data-cy="projets" type="select" multiple name="projets">
                <option value="" key="0" />
                {projets
                  ? projets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/responsable-projet" replace color="info">
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

export default ResponsableProjetUpdate;
