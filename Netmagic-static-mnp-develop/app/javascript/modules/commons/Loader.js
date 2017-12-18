var Loader = createReactClass({
  getInitialState() {
    return {
      show: this.props.show
    }
  },

  contextTypes: {
    router: React.PropTypes.object
  },

  componentWillReceiveProps (nextProps) {
    if(nextProps.show !== this.state.show) {
      this.setState({ show: nextProps ? nextProps.show : this.props.show });
    }
  },

  render() {
    return this.state.show ? ((<div className="loaderOverlay">
      <div className="customLoader"></div>
    </div>)) : (null);
  }
});

Loader.defaultProps = {
  top: "0",
  left: "0"
};

export default Loader;
