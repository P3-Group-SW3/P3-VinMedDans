import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import Header from "../components/Header";
import Section from "../components/Section";


function AboutUsPage() {
    return (
        <div className="Landingpage">
            <Header />
            <Section
            title={'Om os'}
            description={'Vinmeddans er skabt af kærlighed til det ukendte, det eksperimenterende og det sammenhold, der skabes ved at være sammen om et projekt. Vi er 3 gamle venner, der elsker at nyde det gode liv, især over et godt glas vin. Derfor besluttede vi os for at kaste  os ud i at brygge vores egen frugtvin på den hyldeblomst, som vokser  overalt om sommeren. Ingen af os havde prøvet at lave frugtvin før, og  det var i høj grad det ukendte resultat, der virkede interessant og  sjovt. ' +
                            'Vores første frugtvin var så dårlig, at vi ikke ville lade nogen smage på den. Men vores stædighed ville ikke lade os slå ud, så vi besluttede os for at prøve igen. Og  igen. Og til sidst, endte vi med noget af det mest fantastiske, vi havde smagt, som var vores første frugtvin “Hønefuld”. For sjov satte vi den "til salg" på Instagram, og allerede  inden for den første uge havde vi solgt 30 flasker. Folk var meget  overraskede over at frugtvin kunne smage så godt, og den gode respons  gav os blod på tanden og fik os til at begynde for alvor.'}
            imagePosition={"left"}
            imagePath={"http://localhost:8080/api/images/image1.png"}
            />
            <Section
                title={"Vores mission"}
                description={'Det er vigtigt for os at få vores værdier med i produktet. Det startede som et hyggeprojekt, hvilket hurtigt blev til en passion om at producere Danmarks bedste frugtvin. Med fokus på  lækre, naturlige råvarer, omhyggelighed og præcision, elsker vi at  eksperimentere med de traditionelle vintraditioner.' +
                    'Fælles for os alle er det at vi alle er lige investeret i selve  produktionen af vinen, og bærer et fælles ansvar om alt fra plukningen  til flaskningen af vinen. På den måde er vi ikke så opdelte som  virksomhed, og sætter stor pris på at alle beslutninger tages i  fællesskab.'}
                imagePath={"http://localhost:8080/api/images/image2.png"}
                imagePosition={"right"}/>
            <Section
                title={"Vores vision"}
                description={'I dag laver vi frugtvin af høj kvalitet, hvilket vi kan se et stort  potentiale i. Vi vil gerne sprede budskabet omkring vores frugtvin og nå ud til flere danskere. Vi lærer noget nyt hver dag, og glæder os til at se, hvad fremtiden bringer.\n' +
                    '\n' +
                    'Jo længere inde i processen vi er kommet, des mere er vi blevet  ivrige efter at vise, at frugtvin ofte er en overset og undervurderet  drikke. Mange folk kigger skævt når vi siger vi laver frugtvin, da folk  oftest ikke ved hvad det er, og det vil vi gerne være med til at lave om på. Vores mål er, at vores frugtvin skal ses som en lækker, usnobbet  drikkelse.'}
                imagePath={"http://localhost:8080/api/images/image3.png"}
                imagePosition={"left"}/>
            <Footer/>
        </div>
    );
}

export default AboutUsPage;