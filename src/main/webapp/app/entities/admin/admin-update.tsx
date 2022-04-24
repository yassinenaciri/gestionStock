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
import { IAdmin } from 'app/shared/model/admin.model';
import { getEntity, updateEntity, createEntity, reset } from './admin.reducer';

export const AdminUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const entreprises = useAppSelector(state => state.entreprise.entities);
  const adminEntity = useAppSelector(state => state.admin.entity);
  const loading = useAppSelector(state => state.admin.loading);
  const updating = useAppSelector(state => state.admin.updating);
  const updateSuccess = useAppSelector(state => state.admin.updateSuccess);
  const handleClose = () => {
    props.history.push('/admin');
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
      ...adminEntity,
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
          ...adminEntity,
          entreprises: adminEntity?.entreprises?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.admin.home.createOrEditLabel" data-cy="AdminCreateUpdateHeading">
            Create or edit a Admin
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="admin-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nom" id="admin-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Prenom" id="admin-prenom" name="prenom" data-cy="prenom" type="text" />
              <ValidatedField id="admin-entreprises" name="entreprises" data-cy="entreprises" label="Entreprises" type="select">
                <option value="" key="0" />
                {entreprises
                  ? entreprises.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/admin" replace color="info">
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

export default AdminUpdate;
