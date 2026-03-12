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

let laneOpen = null;

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
        laneOpen = lanes[i];

    });
});

closeSelectWindowButton.addEventListener('click', (event) => {

    poolSelectWindow.classList.add('hidden');
    poolSearchInput.value = "";
    laneOpen = null;
});

/*Implement dynamic champion search list*/
const championSearchInputs = document.querySelectorAll('.champion-search-input');
const championSearchLists = document.querySelectorAll('.champion-list');

async function loadAllLists() {

    const urlChampions = `http://localhost:8080/champions`

    try {

        const reqChampions = await fetch(urlChampions);

        const champions = await reqChampions.json();

        renderListsOptions(champions);

    } catch (error) {

        console.log(error);
    }

}

function renderListsOptions(champions) {

    championSearchLists.forEach(list => {

        champions.forEach(item => {

            const championItem = `
                    <li data-id="${item.id}" data-name ="${item.name}" data-lane="${item.mostCommonLane}" data-icon="${item.iconUrl}" data-splash="${item.splashArtUrl}" class="champion-item">
                        <img src="${item.iconUrl}" alt="${item.name}">
                        <span class="champion-name">${item.name}</span>
                    </li>`;

            list.insertAdjacentHTML('beforeend', championItem);
        });

    });
}

loadAllLists();

function refreshLists(event,i) {

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
}

championSearchInputs.forEach((input, i) => {

    input.addEventListener('focus', event => {

        const championList = championSearchLists[i];
        championList.classList.remove('hidden');

        refreshLists(event, i);
        
    });

    input.addEventListener('input', event=>{

        refreshLists(event, i);
    });

    input.addEventListener('keydown', event => {

        if (event.key === 'Enter') {
            event.preventDefault();

            const championList = championSearchLists[i];

            const championVisible = championList.querySelector('.champion-item:not(.hidden)');

            if (championVisible) {

                const fakeClick = new MouseEvent('mousedown', {
                    bubbles: true,
                    cancelable: true,
                    view: window
                });

                championVisible.dispatchEvent(fakeClick);
            }

            input.blur();
        }

    });

    input.addEventListener('blur', (event) => {

        const championList = championSearchLists[i];

        championList.classList.add('hidden');

    })
});


/*Implement dynamic lane filter*/
const homeLaneSearchSelectors = document.querySelectorAll('.select-home-lane-icon');

let laneFilter = null;

homeLaneSearchSelectors.forEach((button, i) => {

    button.addEventListener('click', (event) => {

        homeLaneSearchSelectors.forEach(anotherButton => {

            anotherButton.classList.remove('select-lane-icon-click');
        });

        button.classList.add('select-lane-icon-click');
        laneFilter = lanes[i];
    });
});


const homeScreen = document.getElementById('screen-home');
const resultScreen = document.getElementById('screen-results');
const resultTitle = document.getElementById('results-title');
const resultInput = document.getElementById('result-search-input');
const resultLaneSearchSelectors = document.querySelectorAll('.select-result-lane-icon');

let enemyChampion;

championSearchLists.forEach(list => {

    list.addEventListener('mousedown', event => {

        const championItem = event.target.closest('.champion-item');

        if (!championItem) return;

        const body = document.body;

        const splashUrl = championItem.dataset.splash

        if (!laneFilter) {
            laneFilter = championItem.dataset.lane;
        }

        const lane = Lane[laneFilter];

        const name = championItem.dataset.name;

        body.style.setProperty('--champion-background', `url('${splashUrl}')`);

        resultInput.value = name;

        homeScreen.classList.add('hidden');
        resultScreen.classList.remove('hidden');

        body.classList.remove('home');
        body.classList.add('result');

        resultTitle.textContent = `Counters de ${name} ${lane}`;

        enemyChampion = championItem;
        loadEnemyMatchups();

        resultLaneSearchSelectors.forEach(button => {

            button.classList.remove('select-lane-icon-click');
        });

        resultLaneSearchSelectors.forEach((button, i) => {

            if (laneFilter === lanes[i]) {
                button.classList.add('select-lane-icon-click');
            }

        });
        laneFilter = null;
    });
});


const optionsContainer = document.getElementById('options-modal-list');
const poolContainer = document.getElementById('pool-modal-list');
let cacheAll = null;

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


    const urlAll = `http://localhost:8080/champions`
    const urlPool = `http://localhost:8080/users/me/pool?lane=${lane}`

    try {

        const [allChampions, reqPool] = await Promise.all([
            cacheAll ? Promise.resolve(cacheAll) : fetch(urlAll).then(req => req.json()),
            fetch(urlPool)
        ]);

        if (!cacheAll) {
            cacheAll = allChampions;
        }

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

    const messageName = document.getElementById('add-champ-name');
    messageName.textContent = optionName;

    const messageLane = document.getElementById('add-lane-name');
    messageLane.textContent = Lane[laneOpen];

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
});

const addConfirmationButton = document.getElementById('add-conf');

addConfirmationButton.addEventListener('click', async event => {

    const optionName = optionChampion.dataset.name;
    const alreadySelecteds = Array.from(poolContainer.querySelectorAll('.pool-champion.selected'));

    const nextChampion = alreadySelecteds.find(card => {

        const champion = card.dataset.name;

        return champion.localeCompare(optionName) > 0;
    });

    if (nextChampion) {

        poolContainer.insertBefore(optionChampion, nextChampion);

    } else {

        poolContainer.appendChild(optionChampion);
    }

    optionChampion.classList.remove('option');
    optionChampion.classList.add('selected');
    addConfirmationWindow.classList.add('hidden');

    try {

        const addOnPool = await fetch('http://localhost:8080/users/me/pool', {

            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                championId: parseInt(optionId),
                lane: laneOpen
            })
        });

        if (!addOnPool.ok) {

            throw new Error('O Java nao conseguiu adicionar na pool');
        }

    } catch (error) {

        console.error("Erro ao salvar no banco:", error);

        optionsContainer.appendChild(optionChampion);
        optionChampion.classList.remove('selected');
        optionChampion.classList.add('option');
    }

    optionChampion = null;
    optionId = null;
});

const addCancelButton = document.getElementById('add-cancel');

addCancelButton.addEventListener('click', event => {

    addConfirmationWindow.classList.add('hidden');
    optionChampion = null;
    optionId = null;
});

const removeConfirmationWindow = document.getElementById('delete-modal');

poolContainer.addEventListener('click', event => {

    optionChampion = event.target.closest('.pool-champion.selected');

    if (!optionChampion) return;

    optionId = optionChampion.dataset.id;

    const optionName = optionChampion.dataset.name;

    const messageName = document.getElementById('delete-champ-name');
    messageName.textContent = optionName;

    const messageLane = document.getElementById('delete-lane-name');
    messageLane.textContent = Lane[laneOpen];

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

});

const removeConfirmationButton = document.getElementById('delete-conf');

removeConfirmationButton.addEventListener('click', async event => {

    const optionName = optionChampion.dataset.name;
    const allOptions = Array.from(optionsContainer.querySelectorAll('.pool-champion.option'));

    const nextChampion = allOptions.find(card => {

        const champion = card.dataset.name;

        return champion.localeCompare(optionName) > 0;
    });

    if (nextChampion) {

        optionsContainer.insertBefore(optionChampion, nextChampion);

    } else {

        optionsContainer.appendChild(optionChampion);
    }

    optionChampion.classList.remove('selected');
    optionChampion.classList.add('option');
    removeConfirmationWindow.classList.add('hidden');

    try {

        const removeFromPool = fetch(`http://localhost:8080/users/me/pool?championId=${optionId}&lane=${laneOpen}`, {

            method: 'DELETE'
        });

        if (!removeFromPool.ok) {
            throw new Error('O Java tankou o delete não');
        }

    } catch (error) {

        console.error("Deu ruim ai campeao", error);
    }
});

const removeCancelButton = document.getElementById('delete-cancel');

removeCancelButton.addEventListener('click', event => {

    removeConfirmationWindow.classList.add('hidden');
    optionChampion = null;
    optionId = null;
});

const poolSearchInput = document.querySelector('.pool-option-search-input');

poolSearchInput.addEventListener('input', event => {

    const inputText = event.target.value.toLowerCase();

    const optionItems = optionsContainer.querySelectorAll('.pool-champion.option');

    optionItems.forEach(item => {

        const optionName = item.dataset.name.toLowerCase();

        if (optionName.startsWith(inputText)) {

            item.classList.remove('hidden');

        } else {

            item.classList.add('hidden');
        }
    });
});


/*Result window scripts*/

const resultLaneButtons = document.querySelectorAll('.pool-results-lane-icon');

resultLaneButtons.forEach((button, i) => {


    button.addEventListener('click', (event) => {

        const laneKey = lanes[i];

        poolWindowView.innerHTML = ``;

        poolWindowView.innerHTML = `
        <img id="lane-image" src="assets/lanes/${laneKey.toLowerCase()}.png" alt="${Lane[laneKey]} Lane">
        <span id="lane-name">${Lane[laneKey]}</span>
        `;

        loadAllChampions(laneKey);

        poolSelectWindow.classList.remove('hidden');
        laneOpen = lanes[i];

    });
});


resultLaneSearchSelectors.forEach((button, i) => {

    button.addEventListener('click', (event) => {

        resultLaneSearchSelectors.forEach(anotherButton => {

            anotherButton.classList.remove('select-lane-icon-click');
        });

        button.classList.add('select-lane-icon-click');
        laneFilter = lanes[i];
        const lane = Lane[laneFilter];
        const name = enemyChampion.dataset.name;

        resultTitle.textContent = `Counters de ${name} ${lane}`;

        loadEnemyMatchups()
        laneFilter = null;
    });
});

async function loadEnemyMatchups() {

    let lane = laneFilter;
    if (!laneFilter) lane = enemyChampion.dataset.lane;

    const enemyId = enemyChampion.dataset.id;

    try {

        const [reqMatchups, reqPool] = await Promise.all([
            fetch(`http://localhost:8080/matchups?enemyId=${enemyId}&lane=${lane}`),
            fetch(`http://localhost:8080/users/me/pool?lane=${lane}`)
        ]);

        const matchups = await reqMatchups.json();
        const pool = await reqPool.json();
        const poolIds = pool.map(champion => champion.id);

        const otherMatchups = matchups.filter(matchup => (!poolIds.includes(matchup.myChampion.id)));
        const poolMatchups = matchups.filter(matchup => (poolIds.includes(matchup.myChampion.id)));

        renderEnemyMatchups(otherMatchups, poolMatchups);

    } catch (error) {

        console.log(error);

    }

}

const poolColumn = document.getElementById('pool-column');
const othersColumn = document.getElementById('others-column');

function renderEnemyMatchups(otherMatchups, poolMatchups) {

    poolColumn.innerHTML = '<h2>Sua pool</h2>';

    poolMatchups.forEach(matchup => {

        let poolCard;

        if (matchup.winRate >= 50.0) {

            poolCard = `<div class="column-card win">

                        <div class="column-card-identifier">
                        <img src="${matchup.myChampion.iconUrl}" alt="${matchup.myChampion.name}">
                        <div class="column-card-name">${matchup.myChampion.name}</div>
                        </div>

                        <div class="column-card-icon">
                            <svg  
                            xmlns="http://www.w3.org/2000/svg" 
                            width="100" 
                            height="50"  
                            fill="rgba(200, 171, 113, 0.914)" 
                            viewBox="0 0 6 24" >
                            <!--Boxicons v3.0.8 https://boxicons.com | License  https://docs.boxicons.com/free-->
                            <path d="M16.5 9c-2.11 0-3.99 1.2-4.91 3.05-.9-.09-1.78-.07-2.63.05a15.4 15.4 0 0 0-.13-3.37A3.49 3.49 0 0 0 11 5.5C11 3.57 9.43 2 7.5 2c-1.69 0-3.91.99-4.92 5.69C2 10.43 2 13.51 2 15v2c0 2.95 4.48 5 8.5 5 2.42 0 4.63-.74 6.03-2 3.02-.02 5.47-2.48 5.47-5.5S19.53 9 16.5 9m-.05 8.99h-.02c-.06 0-.13-.02-.2-.02l-.48-.04-.33.36C14.45 19.35 12.56 20 10.5 20 7.06 20 4 18.32 4 17v-2c0-1.42 0-4.36.54-6.89C5.12 5.39 6.12 4 7.5 4 8.33 4 9 4.67 9 5.5c0 .78-.62 1.44-1.41 1.49l-1.19.08.28 1.16c.2.84.31 1.8.31 2.78 0 .76-.06 1.5-.19 2.19l-.28 1.57 1.54-.43c1.24-.35 2.61-.43 3.99-.19l.86.15.26-.83c.46-1.47 1.8-2.45 3.32-2.45 1.93 0 3.5 1.57 3.5 3.5s-1.56 3.49-3.55 3.49Z">
                            </path>
                            </svg>
                        </div>
                    
                        <div class="column-card-data">
                            <div class="column-card-winrate">${matchup.winRate}% WR</div>
                            <div class="column-card-games">${matchup.gamesPlayed} jogos</div>
                        
                        </div>
                    
                    </div>`;
        } else {

            poolCard = `<div class="column-card loss">

                                <div class="column-card-identifier">
                                <img src="${matchup.myChampion.iconUrl}" alt="${matchup.myChampion.name}">
                                <div class="column-card-name">${matchup.myChampion.name}</div>
                                </div>

                            <div class="column-card-icon">
                                <svg  
                                xmlns="http://www.w3.org/2000/svg" 
                                width="100" 
                                height="50"  
                                fill="rgba(200, 171, 113, 0.914)" 
                                viewBox="0 0 6 24" >
                                <!--Boxicons v3.0.8 https://boxicons.com | License  https://docs.boxicons.com/free-->
                                <path d="M12 2C6.49 2 2 5.81 2 10.5c0 2.28 1.09 4.47 3 6.06V20c0 .55.45 1 1 1h12c.55 0 1-.45 1-1v-3.44c1.91-1.6 3-3.78 3-6.06C22 5.81 17.51 2 12 2m5.4 13.28c-.25.19-.4.49-.4.8V19h-2v-3h-2v3h-2v-3H9v3H7v-2.92a1 1 0 0 0-.4-.8C4.95 14.05 4 12.3 4 10.5 4 6.92 7.59 4 12 4s8 2.92 8 6.5c0 1.8-.95 3.54-2.6 4.78">
                                </path>
                                <path d="M8 8a2 2.5 0 1 0 0 5 2 2.5 0 1 0 0-5m8 0a2 2.5 0 1 0 0 5 2 2.5 0 1 0 0-5"></path>
                                </svg>
                            </div>
                    
                            <div class="column-card-data">
                                <div class="column-card-winrate">${matchup.winRate}% WR</div>
                                <div class="column-card-games">${matchup.gamesPlayed} jogos</div>
                        
                            </div>                    
                            </div>`;
        }

        poolColumn.insertAdjacentHTML('beforeend', poolCard);

    });

    othersColumn.innerHTML = '<h2>Outros campeões</h2>';

    otherMatchups.forEach(matchup => {

        let othersCard;

        if (matchup.winRate >= 50.0) {

            othersCard = `<div class="column-card win">

                        <div class="column-card-identifier">
                        <img src="${matchup.myChampion.iconUrl}" alt="${matchup.myChampion.name}">
                        <div class="column-card-name">${matchup.myChampion.name}</div>
                        </div>

                        <div class="column-card-icon">
                            <svg  
                            xmlns="http://www.w3.org/2000/svg" 
                            width="100" 
                            height="50"  
                            fill="rgba(200, 171, 113, 0.914)" 
                            viewBox="0 0 6 24" >
                            <!--Boxicons v3.0.8 https://boxicons.com | License  https://docs.boxicons.com/free-->
                            <path d="M16.5 9c-2.11 0-3.99 1.2-4.91 3.05-.9-.09-1.78-.07-2.63.05a15.4 15.4 0 0 0-.13-3.37A3.49 3.49 0 0 0 11 5.5C11 3.57 9.43 2 7.5 2c-1.69 0-3.91.99-4.92 5.69C2 10.43 2 13.51 2 15v2c0 2.95 4.48 5 8.5 5 2.42 0 4.63-.74 6.03-2 3.02-.02 5.47-2.48 5.47-5.5S19.53 9 16.5 9m-.05 8.99h-.02c-.06 0-.13-.02-.2-.02l-.48-.04-.33.36C14.45 19.35 12.56 20 10.5 20 7.06 20 4 18.32 4 17v-2c0-1.42 0-4.36.54-6.89C5.12 5.39 6.12 4 7.5 4 8.33 4 9 4.67 9 5.5c0 .78-.62 1.44-1.41 1.49l-1.19.08.28 1.16c.2.84.31 1.8.31 2.78 0 .76-.06 1.5-.19 2.19l-.28 1.57 1.54-.43c1.24-.35 2.61-.43 3.99-.19l.86.15.26-.83c.46-1.47 1.8-2.45 3.32-2.45 1.93 0 3.5 1.57 3.5 3.5s-1.56 3.49-3.55 3.49Z">
                            </path>
                            </svg>
                        </div>
                    
                        <div class="column-card-data">
                            <div class="column-card-winrate">${matchup.winRate}% WR</div>
                            <div class="column-card-games">${matchup.gamesPlayed} jogos</div>
                        
                        </div>
                    
                    </div>`;
        } else {

            othersCard = `<div class="column-card loss">

                                <div class="column-card-identifier">
                                <img src="${matchup.myChampion.iconUrl}" alt="${matchup.myChampion.name}">
                                <div class="column-card-name">${matchup.myChampion.name}</div>
                                </div>

                            <div class="column-card-icon">
                                <svg  
                                xmlns="http://www.w3.org/2000/svg" 
                                width="100" 
                                height="50"  
                                fill="rgba(200, 171, 113, 0.914)" 
                                viewBox="0 0 6 24" >
                                <!--Boxicons v3.0.8 https://boxicons.com | License  https://docs.boxicons.com/free-->
                                <path d="M12 2C6.49 2 2 5.81 2 10.5c0 2.28 1.09 4.47 3 6.06V20c0 .55.45 1 1 1h12c.55 0 1-.45 1-1v-3.44c1.91-1.6 3-3.78 3-6.06C22 5.81 17.51 2 12 2m5.4 13.28c-.25.19-.4.49-.4.8V19h-2v-3h-2v3h-2v-3H9v3H7v-2.92a1 1 0 0 0-.4-.8C4.95 14.05 4 12.3 4 10.5 4 6.92 7.59 4 12 4s8 2.92 8 6.5c0 1.8-.95 3.54-2.6 4.78">
                                </path>
                                <path d="M8 8a2 2.5 0 1 0 0 5 2 2.5 0 1 0 0-5m8 0a2 2.5 0 1 0 0 5 2 2.5 0 1 0 0-5"></path>
                                </svg>
                            </div>
                    
                            <div class="column-card-data">
                                <div class="column-card-winrate">${matchup.winRate}% WR</div>
                                <div class="column-card-games">${matchup.gamesPlayed} jogos</div>
                        
                            </div>                    
                            </div>`;
        }

        othersColumn.insertAdjacentHTML('beforeend', othersCard);

    });
}








