import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fournisseur-article.reducer';

export const FournisseurArticleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const fournisseurArticleEntity = useAppSelector(state => state.fournisseurArticle.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fournisseurArticleDetailsHeading">FournisseurArticle</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{fournisseurArticleEntity.id}</dd>
          <dt>
            <span id="prix">Prix</span>
          </dt>
          <dd>{fournisseurArticleEntity.prix}</dd>
          <dt>Article</dt>
          <dd>{fournisseurArticleEntity.article ? fournisseurArticleEntity.article.id : ''}</dd>
          <dt>Fournisseur</dt>
          <dd>{fournisseurArticleEntity.fournisseur ? fournisseurArticleEntity.fournisseur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/fournisseur-article" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fournisseur-article/${fournisseurArticleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FournisseurArticleDetail;
