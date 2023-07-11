const K_WIDTH = 40;
const K_HEIGHT = 40;

const ClickedLocationStyle = {
    position: 'absolute',
    width: K_WIDTH,
    height: K_HEIGHT,
    left: -K_WIDTH / 2,
    top: -K_HEIGHT / 2,
    border: '5px solid #f44336',
    borderRadius: K_HEIGHT,
    backgroundColor: 'white',
    textAlign: 'center',
    color: '#3f51b5',
    fontSize: 16,
    fontWeight: 'bold',
    padding: 4,
}

const CreateMapOptions = (maps: any) => {
    return {
        zoomControlOptions: {
            position: maps.ControlPosition.RIGHT_CENTER,
            style: maps.ZoomControlStyle.SMALL,
        },
        mapTypeControlOptions: {
            position: maps.ControlPosition.TOP_RIGHT,
        },
        mapTypeControl: true,
    }
}

const GoogleMapSetting = {
    ClickedLocationStyle,
    CreateMapOptions
}

export default GoogleMapSetting;