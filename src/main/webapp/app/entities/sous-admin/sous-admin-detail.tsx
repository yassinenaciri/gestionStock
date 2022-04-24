import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sous-admin.reducer';

export const SousAdminDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const sousAdminEntity = useAppSelector(state => state.sousAdmin.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sousAdminDetailsHeading">SousAdmin</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sousAdminEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{sousAdminEntity.nom}</dd>
          <dt>
            <span id="prenom">Prenom</span>
          </dt>
          <dd>{sousAdminEntity.prenom}</dd>
          <dt>
            <span id="mail">Mail</span>
          </dt>
          <dd>{sousAdminEntity.mail}</dd>
          <dt>Entreprises</dt>
          <dd>{sousAdminEntity.entreprises ? sousAdminEntity.entreprises.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sous-admin" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sous-admin/${sousAdminEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SousAdminDetail;
