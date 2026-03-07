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

homeLaneButtons.forEach((button, i) => {


    button.addEventListener('click', (event) => {

        const laneKey = lanes[i];

        poolWindowView.innerHTML = ``;

        poolWindowView.innerHTML = `
        <img id="lane-image" src="assets/lanes/${laneKey.toLowerCase()}.png" alt="${Lane[laneKey]} Lane">
        <span id="lane-name">${Lane[laneKey]}</span>
        `;

        loadAllChampions(laneKey);

        poolSelectWindow.classList.remove('hidden');

    });
});

closeSelectWindowButton.addEventListener('click', (event) => {
    
    poolSelectWindow.classList.add('hidden');
    poolSearchInput.value = "";

});

/*Implement dynamic champion search list*/
const championSearchInputs = document.querySelectorAll('.champion-search-input');
const championSearchLists = document.querySelectorAll('.champion-list');

async function loadAllLists(){

    const urlChampions = `http://localhost:8080/champions`

    try{

        const reqChampions = await fetch(urlChampions);

        const champions = await reqChampions.json();

        renderListsOptions(champions);

    } catch(error){

        console.log(error);
    }
    
}

function renderListsOptions(champions){

    championSearchLists.forEach(list =>{
        
        champions.forEach(item=>{

            const championItem = `
                    <li class="champion-item">
                        <img src="${item.iconUrl}" alt="${item.name}">
                        <span class="champion-name">${item.name}</span>
                    </li>`;

            list.insertAdjacentHTML('beforeend',championItem);
        });

    });
}

loadAllLists();

championSearchInputs.forEach((input, i) => {

    input.addEventListener('focus', (event) => {

        const championList = championSearchLists[i];
        championList.classList.remove('hidden');

    });

    input.addEventListener('input', (event) => {

        const inputText = event.target.value.toLowerCase();

        const championList = championSearchLists[i];
        const championItems = championList.querySelectorAll('.champion-item');


        championItems.forEach(item => {

            const championName = item.querySelector('.champion-name').innerText.toLowerCase();

            if (championName.startsWith(inputText)) {

                item.classList.remove('hidden');

            } else {

                item.classList.add('hidden');
            }
        });
    });

    input.addEventListener('blur', (event) => {

        const championList = championSearchLists[i];

        championList.classList.add('hidden');

    })
});


/*Implement dynamic lane filter*/
const laneSearchSelectors = document.querySelectorAll('.select-lane-icon');

laneSearchSelectors.forEach(button => {

    button.addEventListener('click', (event) => {

        laneSearchSelectors.forEach(anotherButton => {

            anotherButton.classList.remove('select-lane-icon-click');
        });

        button.classList.add('select-lane-icon-click');
    });
});



const optionsContainer = document.getElementById('options-modal-list');
const poolContainer = document.getElementById('pool-modal-list');

async function loadAllChampions(lane) {

    optionsContainer.innerHTML = `<svg  class= "loading-icon" 
                                    xmlns="http://www.w3.org/2000/svg" 
                                    width="200" 
                                    height="200"  
                                    fill="rgba(200, 171, 113, 0.914)" 
                                    viewBox="0 0 24 24" >
                                    <!--Boxicons v3.0.8 https://boxicons.com | License  https://docs.boxicons.com/free-->
                                    <path d="M12 18a2 2 0 1 0 0 4 2 2 0 1 0 0-4m0-16a2 2 0 1 0 0 4 2 2 0 1 0 0-4M7.76 19.07c-.78.78-2.05.78-2.83 0s-.78-2.05 0-2.83 2.05-.78 2.83 0 .78 2.05 0 2.83M19.07 7.76c-.78.78-2.05.78-2.83 0s-.78-2.05 0-2.83 2.05-.78 2.83 0 .78 2.05 0 2.83M4 14c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2m16 0c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2M4.93 7.76c-.78-.78-.78-2.05 0-2.83s2.05-.78 2.83 0 .78 2.05 0 2.83-2.05.78-2.83 0m11.31 11.31c-.78-.78-.78-2.05 0-2.83s2.05-.78 2.83 0 .78 2.05 0 2.83-2.05.78-2.83 0"></path>
                                </svg>`;

    poolContainer.innerHTML = `<svg class= "loading-icon"  
                                    xmlns="http://www.w3.org/2000/svg" 
                                    width="200" 
                                    height="200"  
                                    fill="rgba(200, 171, 113, 0.914)" 
                                    viewBox="0 0 24 24" >
                                    <!--Boxicons v3.0.8 https://boxicons.com | License  https://docs.boxicons.com/free-->
                                    <path d="M12 18a2 2 0 1 0 0 4 2 2 0 1 0 0-4m0-16a2 2 0 1 0 0 4 2 2 0 1 0 0-4M7.76 19.07c-.78.78-2.05.78-2.83 0s-.78-2.05 0-2.83 2.05-.78 2.83 0 .78 2.05 0 2.83M19.07 7.76c-.78.78-2.05.78-2.83 0s-.78-2.05 0-2.83 2.05-.78 2.83 0 .78 2.05 0 2.83M4 14c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2m16 0c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2M4.93 7.76c-.78-.78-.78-2.05 0-2.83s2.05-.78 2.83 0 .78 2.05 0 2.83-2.05.78-2.83 0m11.31 11.31c-.78-.78-.78-2.05 0-2.83s2.05-.78 2.83 0 .78 2.05 0 2.83-2.05.78-2.83 0"></path>
                                </svg>`;

    
    const urlAll = `http://localhost:8080/champions?lane=${lane}`
    const urlPool = `http://localhost:8080/users/me/pool?lane=${lane}`

    try {

        const [reqAll, reqPool] = await Promise.all([
            fetch(urlAll),
            fetch(urlPool)
        ]);

        const allChampions = await reqAll.json();
        const poolChampions = await reqPool.json();

        const poolIds = poolChampions.map(champion => champion.id);

        const optionsChampion = allChampions.filter(champion => !poolIds.includes(champion.id));

        renderPoolOptions(optionsChampion);
        renderPoolSelecteds(poolChampions);

    } catch (error) {

        console.error("deu ruim pae", error);
    }
}

function renderPoolSelecteds(champions) {

    poolContainer.innerHTML = '';

    champions.forEach(champion => {

        const poolChampion = `
            <li data-id="${champion.id}" data-name ="${champion.name}" data-lane = "${champion.mostCommonLane}" class="pool-champion selected">
                <img src="${champion.splashArtUrl}" alt="${champion.name} Splash Art" loading="lazy">
                <span class="pool-champion-name">${champion.name}</span>
            </li>
        `;

        poolContainer.insertAdjacentHTML('beforeend', poolChampion);
    });

}

function renderPoolOptions(champions) {

    optionsContainer.innerHTML = '';

    champions.forEach(champion => {

        const optionChampion = `
            <li data-id="${champion.id}" data-name ="${champion.name}" data-lane = "${champion.mostCommonLane}" class="pool-champion option">
                <img src="${champion.splashArtUrl}" alt="${champion.name} Splash Art" loading="lazy">
                <span class="pool-champion-name">${champion.name}</span>
            </li>
        `;

        optionsContainer.insertAdjacentHTML('beforeend', optionChampion);
    });

}

let optionChampion = null;
let optionId = null;

const addConfirmationWindow = document.getElementById('confirmation-modal');

optionsContainer.addEventListener('click', event => {

    optionChampion = event.target.closest('.pool-champion.option');
    
    if (!optionChampion) return;

    optionId = optionChampion.dataset.id;

    const optionName = optionChampion.dataset.name;
    const optionLane = optionChampion.dataset.lane;

    const messageName = document.getElementById('add-champ-name');
    messageName.textContent = optionName;

    const messageLane = document.getElementById('add-lane-name');
    messageLane.textContent = Lane[optionLane];

    const messagePrep = document.getElementById('add-prep');

    if (messageLane.textContent === 'Suporte' || messageLane.textContent === 'ADC') {
        messagePrep.textContent = 'de';
    } else if (messageLane.textContent === 'Jungle') {
        messagePrep.textContent = 'da';
    } else {
        messagePrep.textContent = 'do';
    }
    messageLane.textContent += '?';

    addConfirmationWindow.classList.remove('hidden');

    console.log(`CLicou no id: ${optionId}`);
    console.log("Fotinha: ", optionChampion);
});

const addConfirmationButton = document.getElementById('add-conf');

addConfirmationButton.addEventListener('click', async event => {

    const optionName = optionChampion.dataset.name;
    const alreadySelecteds = Array.from(poolContainer.querySelectorAll('.pool-champion.selected'));

    const nextChampion = alreadySelecteds.find(card =>{

        const champion = card.dataset.name;

        return champion.localeCompare(optionName) > 0;
    });

    if(nextChampion){

        poolContainer.insertBefore(optionChampion,nextChampion);

    } else {

        poolContainer.appendChild(optionChampion);
    }

    optionChampion.classList.remove('option');
    optionChampion.classList.add('selected');
    addConfirmationWindow.classList.add('hidden');

        try{

            const addOnPool = await fetch('http://localhost:8080/users/me/pool',{

                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({championId: parseInt(optionId)})
            });

            if(!addOnPool.ok){

                throw new Error('O Java nao conseguiu adicionar na pool');
            }

        } catch(error){

            console.error("Erro ao salvar no banco:", error);

            optionsContainer.appendChild(optionChampion);
            optionChampion.classList.remove('selected');
            optionChampion.classList.add('option');
        }

        optionChampion = null;
        optionId = null;
    });
    
const addCancelButton = document.getElementById('add-cancel');

addCancelButton.addEventListener('click', event=>{

    addConfirmationWindow.classList.add('hidden');
    optionChampion = null;
    optionId = null;
});

const removeConfirmationWindow = document.getElementById('delete-modal');

poolContainer.addEventListener('click', event =>{

    optionChampion = event.target.closest('.pool-champion.selected');

    if(!optionChampion) return;

    optionId = optionChampion.dataset.id;

    const optionName = optionChampion.dataset.name;
    const optionLane = optionChampion.dataset.lane;

    const messageName = document.getElementById('delete-champ-name');
    messageName.textContent = optionName;

    const messageLane = document.getElementById('delete-lane-name');
    messageLane.textContent = Lane[optionLane];
    
    const messagePrep = document.getElementById('delete-prep');
    
    if (messageLane.textContent === 'Suporte' || messageLane.textContent === 'ADC') {
        messagePrep.textContent = 'de';
    } else if (messageLane.textContent === 'Jungle') {
        messagePrep.textContent = 'da';
    } else {
        messagePrep.textContent = 'do';
    }
    messageLane.textContent += '?';


    removeConfirmationWindow.classList.remove('hidden');

    console.log(`CLicou no id: ${optionId}`);
    console.log("Fotinha: ", optionChampion);
});

const removeConfirmationButton = document.getElementById('delete-conf');

removeConfirmationButton.addEventListener('click', async event =>{

    const optionName = optionChampion.dataset.name;
    const allOptions = Array.from(optionsContainer.querySelectorAll('.pool-champion.option'));

    const nextChampion = allOptions.find(card =>{

        const champion = card.dataset.name;

        return champion.localeCompare(optionName) > 0;
    });

    if(nextChampion){

        optionsContainer.insertBefore(optionChampion,nextChampion);
        
    } else {

        optionsContainer.appendChild(optionChampion);
    }

    optionChampion.classList.remove('selected');
    optionChampion.classList.add('option');
    removeConfirmationWindow.classList.add('hidden');

    try{

        const removeFromPool = fetch(`http://localhost:8080/users/me/pool/${optionId}`,{

            method: 'DELETE'
        });

        if(!removeFromPool.ok){
            throw new Error('O Java tankou o delete não');
        }

    } catch(error){

        console.error("Deu ruim ai campeao", error);
    }
});

const removeCancelButton = document.getElementById('delete-cancel');

removeCancelButton.addEventListener('click', event=>{

    removeConfirmationWindow.classList.add('hidden');
    optionChampion = null;
    optionId = null;
});

const poolSearchInput = document.querySelector('.pool-option-search-input');

poolSearchInput.addEventListener('input', event =>{

    const inputText = event.target.value.toLowerCase();

    const optionItems = optionsContainer.querySelectorAll('.pool-champion.option');

    optionItems.forEach(item=>{

        const optionName = item.dataset.name.toLowerCase();

        if(optionName.startsWith(inputText)){

            item.classList.remove('hidden');

        } else {

            item.classList.add('hidden');
        }
    });
});








