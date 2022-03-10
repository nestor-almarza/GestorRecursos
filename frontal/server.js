const express = require('express');
const app = express();

const PORT = 4200;

app.use(express.static('./dist/frontal'))

app.get('/*', (req, res) => {
	res.sendFile('index.html', {root: 'dist/frontal/'})
});

app.listen(PORT, () => {
    console.log(`Server listening on port ${PORT}`);
});