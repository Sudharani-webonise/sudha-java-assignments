var Alert = React.createClass({
    closeAlert() {
        this.refs.close.style.display = 'none'
    },

    render() {
        return (
            <div className="alert customAlertMsg fade in" ref='close'>
                <a href="javascript:void(0);" className="close" data-dismiss="alert" aria-label="close" onClick={this.closeAlert}>&times; </a>
                <strong className='message'></strong>
            </div>)
    }
})
export default Alert;
