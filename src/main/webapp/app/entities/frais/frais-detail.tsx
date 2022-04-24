import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './frais.reducer';

export const FraisDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const fraisEntity = useAppSelector(state => state.frais.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fraisDetailsHeading">Frais</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{fraisEntity.id}</dd>
          <dt>
            <span id="prix">Prix</span>
          </dt>
          <dd>{fraisEntity.prix}</dd>
          <dt>
            <span id="desscription">Desscription</span>
          </dt>
          <dd>{fraisEntity.desscription}</dd>
          <dt>Projet</dt>
          <dd>{fraisEntity.projet ? fraisEntity.projet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/frais" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/frais/${fraisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FraisDetail;
