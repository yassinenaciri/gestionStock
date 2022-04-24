import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './responsable-projet.reducer';

export const ResponsableProjetDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const responsableProjetEntity = useAppSelector(state => state.responsableProjet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="responsableProjetDetailsHeading">ResponsableProjet</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{responsableProjetEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{responsableProjetEntity.nom}</dd>
          <dt>
            <span id="prenom">Prenom</span>
          </dt>
          <dd>{responsableProjetEntity.prenom}</dd>
          <dt>
            <span id="mail">Mail</span>
          </dt>
          <dd>{responsableProjetEntity.mail}</dd>
          <dt>Projets</dt>
          <dd>
            {responsableProjetEntity.projets
              ? responsableProjetEntity.projets.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {responsableProjetEntity.projets && i === responsableProjetEntity.projets.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/responsable-projet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/responsable-projet/${responsableProjetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResponsableProjetDetail;
