/*Implement dynamic pool selection modal*/
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

    });
});

closeSelectWindowButton.addEventListener('click', (event) =>{

    poolSelectWindow.classList.add('hidden');
    
});

/*Implement dynamic champion search list*/
const championSearchInputs = document.querySelectorAll('.champion-search-input');
const championSearchLists = document.querySelectorAll('.champion-list');

championSearchInputs.forEach((input,i)=>{

    input.addEventListener('focus',(event)=>{

        const championList = championSearchLists[i];

        championList.classList.remove('hidden');

    });

    input.addEventListener('input',(event)=>{

        const inputText = event.target.value.toLowerCase();
        
        const championList = championSearchLists[i];
        const championItems = championList.querySelectorAll('.champion-item');


        championItems.forEach(item=>{

            const championName = item.querySelector('.champion-name').innerText.toLowerCase();

            if(championName.includes(inputText)){

                item.classList.remove('hidden');

            } else{

                item.classList.add('hidden');
            }
        });
    });

    input.addEventListener('blur',(event)=>{

        const championList = championSearchLists[i];

        championList.classList.add('hidden');

    })
});


/*Implement dynamic lane filter*/
const laneSearchSelectors = document.querySelectorAll('.select-lane-icon');

laneSearchSelectors.forEach(button=>{

    button.addEventListener('click',(event)=>{

        laneSearchSelectors.forEach(anotherButton=>{

            anotherButton.classList.remove('select-lane-icon-click');
        });

        button.classList.add('select-lane-icon-click');
    });
});





