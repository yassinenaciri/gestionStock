import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEntreprise } from 'app/shared/model/entreprise.model';
import { getEntity, updateEntity, createEntity, reset } from './entreprise.reducer';

export const EntrepriseUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const entrepriseEntity = useAppSelector(state => state.entreprise.entity);
  const loading = useAppSelector(state => state.entreprise.loading);
  const updating = useAppSelector(state => state.entreprise.updating);
  const updateSuccess = useAppSelector(state => state.entreprise.updateSuccess);
  const handleClose = () => {
    props.history.push('/entreprise');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...entrepriseEntity,
      ...values,
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
          ...entrepriseEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.entreprise.home.createOrEditLabel" data-cy="EntrepriseCreateUpdateHeading">
            Create or edit a Entreprise
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="entreprise-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nom" id="entreprise-nom" name="nom" data-cy="nom" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/entreprise" replace color="info">
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

export default EntrepriseUpdate;
