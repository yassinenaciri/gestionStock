import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './admin.reducer';

export const AdminDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const adminEntity = useAppSelector(state => state.admin.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adminDetailsHeading">Admin</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{adminEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{adminEntity.nom}</dd>
          <dt>
            <span id="prenom">Prenom</span>
          </dt>
          <dd>{adminEntity.prenom}</dd>
          <dt>Entreprises</dt>
          <dd>{adminEntity.entreprises ? adminEntity.entreprises.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/admin" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/admin/${adminEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdminDetail;
