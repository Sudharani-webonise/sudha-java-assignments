import React from 'react';
import TestUtils from 'react-addons-test-utils';
import expect from 'expect';
import App from '../App';

describe('App', () => {
  it('renders without problems', () => {
    var root = TestUtils.renderIntoDocument(<App/>);
    expect(root).toExist();
  });
});
