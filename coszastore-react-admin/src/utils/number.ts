const number = {
    numberFormatter: (value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ','),
    stringToDateTime: (value) => value.replace(/\$\s?|(,*)/g, ''),
};
export default number;
