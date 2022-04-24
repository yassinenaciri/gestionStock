import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './paiement.reducer';

export const PaiementDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const paiementEntity = useAppSelector(state => state.paiement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paiementDetailsHeading">Paiement</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{paiementEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{paiementEntity.nom}</dd>
          <dt>
            <span id="prenom">Prenom</span>
          </dt>
          <dd>{paiementEntity.prenom}</dd>
          <dt>
            <span id="mail">Mail</span>
          </dt>
          <dd>{paiementEntity.mail}</dd>
          <dt>Item</dt>
          <dd>{paiementEntity.item ? paiementEntity.item.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/paiement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/paiement/${paiementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaiementDetail;
