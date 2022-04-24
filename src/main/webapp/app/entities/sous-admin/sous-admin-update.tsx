import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEntreprise } from 'app/shared/model/entreprise.model';
import { getEntities as getEntreprises } from 'app/entities/entreprise/entreprise.reducer';
import { ISousAdmin } from 'app/shared/model/sous-admin.model';
import { getEntity, updateEntity, createEntity, reset } from './sous-admin.reducer';

export const SousAdminUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const entreprises = useAppSelector(state => state.entreprise.entities);
  const sousAdminEntity = useAppSelector(state => state.sousAdmin.entity);
  const loading = useAppSelector(state => state.sousAdmin.loading);
  const updating = useAppSelector(state => state.sousAdmin.updating);
  const updateSuccess = useAppSelector(state => state.sousAdmin.updateSuccess);
  const handleClose = () => {
    props.history.push('/sous-admin');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getEntreprises({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...sousAdminEntity,
      ...values,
      entreprises: entreprises.find(it => it.id.toString() === values.entreprises.toString()),
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
          ...sousAdminEntity,
          entreprises: sousAdminEntity?.entreprises?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.sousAdmin.home.createOrEditLabel" data-cy="SousAdminCreateUpdateHeading">
            Create or edit a SousAdmin
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="sous-admin-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nom" id="sous-admin-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Prenom" id="sous-admin-prenom" name="prenom" data-cy="prenom" type="text" />
              <ValidatedField label="Mail" id="sous-admin-mail" name="mail" data-cy="mail" type="text" />
              <ValidatedField id="sous-admin-entreprises" name="entreprises" data-cy="entreprises" label="Entreprises" type="select">
                <option value="" key="0" />
                {entreprises
                  ? entreprises.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sous-admin" replace color="info">
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

export default SousAdminUpdate;
