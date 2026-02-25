const homeLaneButtons = document.querySelectorAll('.pool-home-lane-icon');
const Lane = Object.freeze({
    TOP: 'Top',
    JUNGLE: 'Jungle',
    MID: 'Mid',
    ADC: 'ADC',
    SUPPORT: 'Suporte'
});
const lanes = Object.keys(Lane);

const poolSelectWindow = document.getElementById('pool-select-overlay');
const poolWindowView = document.getElementById('pool-lane-view');



const closeSelectWindowButton = document.getElementById('exit-pool-selection');

homeLaneButtons.forEach((button,i)=>{


    button.addEventListener('click', (event) =>{

        const laneKey = lanes[i];

        poolWindowView.innerHTML =``;

        poolWindowView.innerHTML=`
        <img id="lane-image" src="assets/lanes/${laneKey.toLowerCase()}.png" alt="${Lane[laneKey]} Lane">
        <span id="lane-name">${Lane[laneKey]}</span>
        `;

        poolSelectWindow.classList.remove('hidden');
        poolSelectWindow.classList.add('active');
    });
});

closeSelectWindowButton.addEventListener('click', (event) =>{

    poolSelectWindow.classList.remove('active');
    poolSelectWindow.classList.add('hidden');
});