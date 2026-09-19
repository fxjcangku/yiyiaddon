package com.yiyiaddon.l.f;

import com.yiyiaddon.l.b.p;
import com.yiyiaddon.l.j.n;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.PlayerSkin;

public final class e extends com.yiyiaddon.l.f.a {
   private static final float jg = 18.0F;
   private static final float jh = 10.0F;
   private static final float ji = 12.0F;
   private static final float jj = 10.0F;
   private static final float jk = 14.0F;
   private static final float jl = 15.0F;
   private static final float jm = 35.0F;
   private static final float jn = 44.0F;
   private static final float jo = 12.0F;
   private static final int ty = 4;
   private static final float jp = 114.0F;
   private static final int tz = 3;
   private static final float jq = 46.0F;
   private static final float jr = 32.0F;
   private static final float js = 30.0F;
   private static final float jt = 8.0F;
   private static final float ju = 30.0F;
   private static final float jv = 44.0F;
   private static final float jw = 24.0F;
   private static final float jx = 26.0F;
   private static final int tA = 6;
   private static final int tB = 5;
   private static final int tC = 8;
   private static final int tD = 5;
   private static final float jy = 28.0F;
   private static final int tE = 3;
   private static final float jz = 22.0F;
   private static final float jA = 104.0F;
   private static final String FB = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
   private static final DateTimeFormatter c = DateTimeFormatter.ofPattern(
      (String)com.yiyiaddon.m.b.a<"s2tl05lhqkry8n","faRJpFkGvzmToimhZ731eS+3jg/5aygINdEoKCpV3vjq3CYvMo5A51fglWE=",-4758417509030768666,1771522978750990882,-1808798545785812678,5471787533682130663>()
   );
   private static final List<String> dr = List.of(
      (String)com.yiyiaddon.m.b.a<"s75e1fabqt7cf","MRwXcOoCpFX9XDp/GiwNRY8GCVHvhV+AGfhbyil15a0=",3540123451044406251,919986351262359662,-4658269862306548143,-8439731956431670465>(),
      (String)com.yiyiaddon.m.b.a<"s2s3d5izw1eivh","AkmnPt8Gd8i2XED84JMkwb2C0fg7oiCAjI7wxtQaxFw3OA==",926475167916865782,-369420627435855255,-962458651203470100,3528465113118911990>(),
      (String)com.yiyiaddon.m.b.a<"s1ykpa851rx794","fpq3HbI5y1fuNxPK5YgPh2lSTZ1n7YTNzakUfLdq/bGsqw==",-3591454876639894601,-8004546257474928315,6602075651214863538,-351001095061365070>(),
      (String)com.yiyiaddon.m.b.a<"s5b2737au17gr","xyuxQUcYU9VF7qgmE9lXDZ/Zgx9q8lMnHyBJeUDpx6S0Qw==",-3454124721679325623,-1199809039491073772,2962794302266375265,-4421382229553728756>(),
      (String)com.yiyiaddon.m.b.a<"s8ho7cub6fssg","t/Nyx08xA+MRYyaWgQzZTXo0x3aBTsdhA8jcJebeIAnFww==",4498555433782843286,5867287313848127055,3865881497904831819,-4598764035308688965>(),
      (String)com.yiyiaddon.m.b.a<"s1v2jmtsrvq6uw","lT5jwZ7tmYnhZD3BgIZI0PDWnppdP7PNaSErpCbpLPTyPYCo",-7472139924401525245,-3503917266736160355,-8237248947974128735,-4567472020661450859>(),
      (String)com.yiyiaddon.m.b.a<"s36mtfaw9ipcr0","G6xYcpl+iT7UuBYDBebdAIShjyW+dArBWILDSViSw1qwvTqeDFl+t/wdg2U=",8374376393999854693,426335189404759267,-6971440429004185716,-996475507164480630>(),
      (String)com.yiyiaddon.m.b.a<"s1a3xoxo39br6n","n7Z+bawBZJcNQQgYIUbSdDV6xVQx110qG1KWreu7FH+BfsJA5ZWytoKtf0eEXM00VMRuB/759Yk=",7253255491206968935,-8469903860098967691,3385044275077795211,5006902010386497878>()
   );
   private static final long bh = System.currentTimeMillis();
   private static final int tF = 2278750;
   private static final int tG = 15680580;
   private static final int tH = 16096779;
   private static final Map<String, String> bh = Map.ofEntries(
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s3370q75kdblrg","XagvDKLGC1I5w8RkJ8Pzq/gTolGFkyJGfXB4gz66P14=",2683646902580444410,-4944600862755699526,2834998233791237638,2228238516469104377>(),
         (String)com.yiyiaddon.m.b.a<"s1kvy0sixipbh3","iroy0nwGdhGGJfcMlN3niu3YycD8CHUbkl1OnBFS6vQ=",-940507066480567082,-8139053119377443859,-9153540648732519077,-2568628876208480216>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"sm7bzum6w8hf","nq6VMEqgtr2JVdPNnRwAhd0mD0y2zKLCN/EE2X9wcds=",7470069338848845484,-7371802390606494023,-6384029035469032073,-1362188632374598192>(),
         (String)com.yiyiaddon.m.b.a<"s99m0sd7elv0g","Oc+ZRogBO0xQdEvwq5p/dq+lY/JnzoLX7LOzq/XUeow=",-8049933987676945391,1885774005646816643,-4018402776077304891,4468639866409721978>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1hmve4857te5p","6xgDFSmG630lXhCq4B6KMuIsP10KTQmifspYM1ojFQE=",1960996409618547503,5687188775695715439,-1381642351772896800,-3673215868341725161>(),
         (String)com.yiyiaddon.m.b.a<"s1s2wgqf9n4rwz","pfTytTMvhate6tWhzSEn08rADmOagAe3iKBmaUjrJ3o=",-718718970404057375,-4052345454102231806,7650855412452092518,-394134201652181144>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1n9ipf8oqdycf","ToAMenF+dYcQVeJkxbQ6VK5cuitS2oviDBXJOFKkW0M=",9009455451316167680,-7552063149366728065,-7104950303359192413,-1942304706614173236>(),
         (String)com.yiyiaddon.m.b.a<"s75ih7304swha","tKUH4J0J0AvX7k80aeyY3V9n0Mm5kvSsH17eV47HX7o=",4167328800145711629,-8352898688071863147,-4489897764221936508,-4817087468597937513>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2c4fh6bjwbjvk","My17ynyvBmAZlvyb75E6erVNyYRNwf+fPNr4vld/rHk=",2033822211636166385,-8040259300760474467,-3010521529038587533,-7195654107230507976>(),
         (String)com.yiyiaddon.m.b.a<"s3knefwp8snhdx","AzRw6+7K4gnUFWX3ATNQYFREpeTDBkWFvuLlmCHaucc=",430088402101032086,3219225262011928791,-2210286952453585564,4036104764417128739>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s22psgsz0ws465","5JkwauCdf3mAzTLlE8XRGMyz8berYiY2UgPwFqqyvm8=",-933922783228645311,5371168757506144048,-2637947231620627189,3094292478998342094>(),
         (String)com.yiyiaddon.m.b.a<"s1py25fio2j9i7","4ArbXw1OJnJFmEv6YclCCBUirCtg8zvd1JI8283Rsrk=",4576544841138671468,-2191612357874316802,-3235101952151548765,1089417267499469726>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1ng7mrzexx2cb","ZchBEtj6xVw9K1NqMbPFY3j6gEGgLhJ0bggppG0+FLg=",-3486087815821848142,-283203542357633646,-6489632339541077441,5984402008360730064>(),
         (String)com.yiyiaddon.m.b.a<"s105b4loskrt86","MxCfvl6anWE7+Kj1XlThy7VISbz0kFFCycbF1COmeu9X2A==",-6788542527101745889,7888806309300644328,6888398584720300528,-8642675947745425410>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2ln1ijb5i5owq","zyfchhl35QCkN3bttXZDnXWIzH4uVNNmsWotYXRR6Q0=",-7174804925102433282,4010363685312027283,-2728301044956967760,5209085818481789729>(),
         (String)com.yiyiaddon.m.b.a<"svr30batacsgr","63RAtbgGSO3+Ty7ULsUB51XgCTutWQxpoKmIjWmAWg9uqvfM",-4838540320539860296,429305727996395876,2916997679074204430,-4999507842950175201>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1lsdj8com85w9","ADdiS8Eygxfcs4Exs9WDgW0p6X20GuZQgJDgEKq4eDA=",-4071120255650957836,4773681823515433226,-1321953187069668839,-6909376379800982089>(),
         (String)com.yiyiaddon.m.b.a<"s2rfp2mbai2khk","QVQEF9uzij0gWozGWkn6C/j7OeetkwEOSUWwBX+ynyE=",-4730745770428446701,5683286081188151762,-2829697890250312073,-3502847514472309527>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1yc7817hiecls","3qidppKCMooojz4R3r+Nkj10E1sTn2IhAg9eIZtlW2Q=",-704262806621266651,-957944718169591500,301031745969439779,4320687616673842068>(),
         (String)com.yiyiaddon.m.b.a<"s102ed2iwtnbec","x7YnCK3RzlUGPYbteEC8FE0v9llZ0EJuiJYT80WnN8E=",-1217668826997022178,1115712970901229546,1197507364502649153,-5145181637314545149>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s22e8fitrf9083","V9SGP8nQCdSFziAOfIeWRH9qCRgdyE+nOJ4G/cwr8L8=",905170542775569639,1648286292220502561,1625009713646578548,3214393815415613020>(),
         (String)com.yiyiaddon.m.b.a<"s2hklul0g9v4z2","PVYBgicGiLzj428O3lLE8Epl2q2xCaUl1oeMBVu78GfvcQ==",-8919874922209746187,4417744097976239061,7872719765877511004,-102031663065641920>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s23cgtug0933g0","0SJjVUPEyNljUd39nmJB8iD0PfnabRdG5BP67ZKuOdw=",2752977308484295527,3794463179422067564,8031963352518317090,-6273517274797913311>(),
         (String)com.yiyiaddon.m.b.a<"sj5dlm8up363j","PAVi5vWhoZXiUPHWakKqw19XSZtvgQmOa2en07o0Pwr5FFFs35g=",-780145111615675028,8632767214468931453,354366671498397180,-2770939330582379440>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1rqlaqvul8t3p","fVZ+p/OXPH8/ciFxZrzWJKFVBFqIXnQ4y0KOHWW53Es=",-6697951140124070620,-6854795809690602247,-7989303401390955564,-7821426861566519351>(),
         (String)com.yiyiaddon.m.b.a<"s22ux61txvc78z","KPU3pK5ez6AvgUp7k//6ePH4JUWjMOhu6X45mEceR0Y=",6516181989884497603,-4187378314147432267,-2597151870779119439,-6668609460083989718>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1zp0kyiccbjl1","trpMPhRWckyvuaPhLr6XfrDKAa9W/n3eenTLOXZO0zY=",8794730485732811482,-2387634007084288299,-1864042211086829091,-5742215182715787488>(),
         (String)com.yiyiaddon.m.b.a<"s1pczgbrhsj36d","dkOErirrVlEeo0HtTRZaYQTyxZRpq/rS7+AAvPTflTk=",-6196589498250076137,-4785700840988119736,-4938892057563151759,-5521333358094121912>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"swmjqogwzx44g","GLOtthv7B3NmTD6Lb/BI7j/IsuvyHg+uUorSOgPzuK4=",-7252386235197242347,-607763315164040324,-9036936951258025526,-6087087935186482623>(),
         (String)com.yiyiaddon.m.b.a<"s3q5us5d0r54uw","LzfalcmMOAadP4xClVRsWvbu6DQMxd5JovEBoSs9ea6PWA==",-1410361404012497694,1760993087258644588,4159465379627656416,-8272831436322321629>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2k1uvtoinuuz9","uy1aZC+j92LvMvJgp0Qs1jWSQIZ/eXusL3iszpfW144=",53441830099624352,-8075064707228821076,-2574820214139760428,-8151634425035027403>(),
         (String)com.yiyiaddon.m.b.a<"sfznrao9to2ds","NjRxdilalMvnxAqzf8/rvu3kkIabLal3fUqcXj0ZY1PQHw==",3482935288299066766,-3407699519238455317,7069064618300905401,-3427347396203045177>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1lrmajmilww79","/qUk5kgtX77oSQnBn+4m/hVF0gaNI0LwRheJXFoFT5w=",-7538255761997556442,-3051752889452766635,3049460338490983764,-6259659457968198744>(),
         (String)com.yiyiaddon.m.b.a<"s27li40cre4xz5","pNKvvCvRyYBZh4eWsoRF+YvlaEW2RfPfjTARDk//8ZU=",-7038445385447827085,6459072750094860645,3011149182583682078,5317283900636764523>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1n62l9lqw0oo0","x0KIM/oeuQBauXoV/kcExiadMa4I/vapSwg0wK/ag28=",-3185902430711432119,8116036887586218531,2626339590295188737,2835556203271615541>(),
         (String)com.yiyiaddon.m.b.a<"s23lqklugdrns4","ryQRIlmLv0bz0aECYhN0wwqUALY1UZfUjBfjpwRs+JA9sw==",3140011584371924245,7886350128869877342,-2988533988770613607,-5945846838881518610>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s3hdkyam6jk5vd","pobvOHgaE5VtuKdpPe1QoVk+rkRsjhuOYN5GMvRVVh0=",6753858278671392368,1863030223722402269,4733475932706654046,-8242046113755044920>(),
         (String)com.yiyiaddon.m.b.a<"s352bpoz5eff70","FrC2ILoRfV3E6r4Y4LhE7qVf5ZcjNZhfRg9XB7Ui8tk=",-3109934318463946606,-3095673327783815289,-3149244426284597252,368604001823586890>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2yl025l5fq5bz","7SKrMx//cRPqUS1sbAd3yIW8KxNmi6EaAbrWOufwK4w=",8304235262012486496,-1785249677042451174,-7642719300704790546,-6181678736849155979>(),
         (String)com.yiyiaddon.m.b.a<"s14rjipunlfhkg","Di1z5vh7KfG4FECLZkyXC4lvobyP2KXKTuy3S3P1N/s=",-7410871194365007355,8004298792039053645,-3722395880187687400,5310710801995072268>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2kztrsgq8kbhj","JAGu8bvnynGHCanAYlttC7zJyRf7Rc6xAeRUFKakchY=",-6344100313074527262,-8234445548265388529,-5204342672798240242,6520216833013933681>(),
         (String)com.yiyiaddon.m.b.a<"s2vrznprbzl4l6","UdeXTZLh/oOMLCIjab2YTppl5MQSLbSVakUnIOHr2MI=",-8489023750573998553,-3048382585027716410,-6661633052900599844,-5811569969080576540>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s3meb3m016ln5r","671lAF5p7Pl+K5EQvPQsCoRWlC7ldoNhCrOsyGqHMZI=",4596104242383669666,3510978084529807132,3865679824945313323,-5293136360582128198>(),
         (String)com.yiyiaddon.m.b.a<"s35pnqswuhtvez","IgdwI1eSUp41UcDnGxVtiy4l5SyiiptQVOtwO7ucD98=",-1340885550439171785,-1617915809908543901,3216658807690642855,-2380576745793138530>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2vn9qzkf44rua","++wAi3TYx1riSZuSNw1jCHNOdASFSUeL+1NV+OeHtE4=",2869986211913262522,36618331765314951,5034515522559953653,-6457607591904957459>(),
         (String)com.yiyiaddon.m.b.a<"srevsof60dxjq","H6+Sp5Ew0Ye43wd/hFbPR5vMYLdLVRyAMsZzMvO9HFR6Sw==",-3678323540532453944,-8375326811243200254,3453149900039443407,99398686817625036>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s3vnz37193cgsi","E+ozACwVxSMY+BaxUVAW1Miwpj0Fz180IuyqZSmBNbk=",7965628699891998841,85765419856156873,3794718595890330743,6578177179300582248>(),
         (String)com.yiyiaddon.m.b.a<"s2kouxptgmoym4","7W9nzZA0glPODhxGmnWoFFDFgeQ2rlo3n+1Pksozv6Y=",9215594136429936933,-6602238803090874925,335501643983236966,270574173694199055>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1n51d54saw87z","inQmAkBadhgwD4yQmazv8iD9mJ1ayoUaN+hKQBj+O7E=",6904276039683476297,-1897622726057411624,4267572833915771129,-5707885520177122213>(),
         (String)com.yiyiaddon.m.b.a<"s2el5ltajdpvmm","m/eu0S53WrRe28u+QTnN1DQuASuYHDaw1iAsJgiaqEXmVg==",8566469747154449796,-584728653498674132,370930745629664848,8335526911762546628>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2wbhxp3mt09sm","lu8m9oBoY3VIRAhekgBV7tWG3vsg7yJUBpo45zK3Kus=",8684209265161706266,4790775578933863735,6727087318040114658,4009613142134174983>(),
         (String)com.yiyiaddon.m.b.a<"s37dilhn4nongq","SoLd6VZ6Y2JpRJHL4LjuJbhPUxCnl3EV/RU9059BXv0=",-8903109263145935590,-1281678016057378755,803658328154232569,4332833960196392283>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s24xplm5mchkt8","W3BsNgf5qWpBJUf2cEBpXkHIdAjQIyhAK39Nfg+SJrU=",-8741465033562475155,2016832112442292757,-7076305857681292150,5389565221849266879>(),
         (String)com.yiyiaddon.m.b.a<"swu85dciyagsq","hKtxyffw+grZyfTH+VzVhCISpmduRuQReo/zKZQpB3Y=",4193017469767256639,-4914994596701456484,5374330880384795794,3229895551047227919>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s19dogjb43xqtw","b/kbAmhTNeGYkMDB2gm5eA7EbCebfbwodvxbWFhXgwY=",2579598019641508569,1020624274414974242,-2026502732389623181,-3127961576460476815>(),
         (String)com.yiyiaddon.m.b.a<"s20p6ba75jf8ee","AZ+Hzz/dsJKMKatRI+Lo6IOCEsJD/ZRrehzpA9Hzg5I=",-5191582994558843890,5314832086221645247,-8657431824262991851,7774066392947080732>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1qg3j438vf2uf","bMV+rtYXtDT/ffTvMXiFnN7Fz8caKLYBYCe9WIbqMN0=",-5543096268155591781,-491081921576282772,-5424379323325289319,-3472358386644909396>(),
         (String)com.yiyiaddon.m.b.a<"s5mq8ml25kh2s","IZfn7ZDrw4M10SUZ2j//6YjGgUJy/E2s+5MWq3qeBpQ=",3874150533983836226,2387230058843337649,4301272394877711249,-4643500198469398302>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s238p96ehqxy2v","fJc0siPt9rD2kmpVuth3tEZindvbypfWsGB1wz7LwWc=",6692491146618881566,-4254834375231823615,456554706496473894,8288287643805430665>(),
         (String)com.yiyiaddon.m.b.a<"s1610l7cmtooag","T6YDMbOThqU0dE3nBTvauz36b5sTX5vpMz1/BvBvnSU=",8376998958592597780,-1839181460333163386,157979967860650330,-2417328588469048644>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s3asjaqfd95d9m","CnQ/EEcHkZJZfinl536YnplWXlHNqQsAmMcZkfBlC6k=",-589706181242881561,7833007944346644178,7820377299534240083,-7778845460471322161>(),
         (String)com.yiyiaddon.m.b.a<"s2edykkhreeacu","Q0Q8cGS+Jc8Z3dClkL+vO2Us9n+Duw3NzPo7TiMcDi3s8Q==",8747922634289738555,-7908091572807327723,-367252409925459564,6315534634368957008>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2e9eq2agcjbfw","+FE9acbrDdE0vrUVZ7oDyrqMIMj9v6sLGH/s0uuD4MY=",-6485419645455641288,3586371528123204536,-9200614175101714876,4579079361114750997>(),
         (String)com.yiyiaddon.m.b.a<"s39c9ivnye6z3k","9un+mI/l95gSsJWhiNwCoNLTLF4o1VXJy/9hpBNwyByPYQ==",-7668398559037225383,8412983274976264932,7941245118488315357,-8220256157811149641>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"sjqbpj852jf3p","gk9HvbWi9GEjz9tYqYpBj2+VqglfkaUB/dLI7WbOrU8=",-7698614675633373442,-732538459490153371,-853991715362090221,5182546600581281658>(),
         (String)com.yiyiaddon.m.b.a<"sx72mo9laygqp","GKHngSJAa9NMf/6uzp5QTPOZeaRuRXANfrbSzQyqvwjddQ==",-5204370545477320199,830729846209087674,-3895963186115605876,-3536041167302539486>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2diosjvq1el38","6gH4JydR0Qoi4z5LCluW1K6vfFzSHDtVIj2vQlRojPY=",312552110984658425,6852844164552148154,-2865560025293291727,206328132637180547>(),
         (String)com.yiyiaddon.m.b.a<"s2ugeeuqjwzgwd","ZIEFBWM5X1Orqx4ejeBBTNeGZCZEQWoyfpps9qqL5b29vw==",6202262582878113297,8296183385456887642,2970588294388538084,8945142317332690193>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s25pjvv4ci9m0o","FACT0fStm7LrSZ6lmN2Uzb+UVA1vu4R8QcBHBjTFMsM=",6309736351707194130,5814450438360880364,3117353770604797159,-3855498101384978335>(),
         (String)com.yiyiaddon.m.b.a<"s2k9c5umclrq8","2KwTImKcpINIoU5tcnxCrXzwKETLg+4eWzfThHnClkE=",-1086032747452975285,-1751402924039413267,-6756453774115476514,-359465642643017582>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s13jkx6u4sgs6z","slTX2xEha+pnnp6yz3lMsF7TWFPDvSJtj+2D6kAnEg8=",8533907494218553845,3222916022214727088,-4981597872289885557,6209423126678525731>(),
         (String)com.yiyiaddon.m.b.a<"sqk7qpr7tufqr","2gPNM3aUiJUO8gRnFACx3eIfZ+GCqwT+17BrDca228srDlqE",-1049312011528756463,-4531870994255305573,-963096194848353109,-759159646897749279>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"scgrtziea1ow8","Aipwm05ZAlr0M0FmbgtRvf8ZgWhKEjaI/IE2UOWa4Ko=",5786909730247448210,-650110993346732020,-1422321996544776220,-3075562713784170108>(),
         (String)com.yiyiaddon.m.b.a<"s3kp7epdnw4xx","B2H/sLk+vKP7mkUzO93zOYNYiLB3FUk65LJGP1cFpbQalw==",3124004735501520702,8224606016864085751,5141696895491159658,1367682417313223845>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s4kh7tf798rbm","y1PXMKQULkQsEvoT8ggi4SnqYcS6nMXdf32yHgYOdGU=",-3384680238190660847,-3875319885741040190,-5623113772255400639,-776981291845656642>(),
         (String)com.yiyiaddon.m.b.a<"s2twkgvsslliz8","WCw789FD6eWdiYo2brreSxDiMhNZogNRNyN8VTtKs7X8rQ==",-7255230814126378063,3233084758801724123,-2260277534569713567,-8570236032136630464>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"stbxfmezhc3s8","JYZ9TwlU6aWbKSsFYfy70F/jyDuo5sNg/FZ/rKR9aco=",592410355576654588,1453051115069277699,-8976300665827024475,6352153585269502185>(),
         (String)com.yiyiaddon.m.b.a<"s3tfsa21ag2o2j","MRs29mluvhXrT+HLURdpS1PeFJRXyhQvPdmdKqL5kqmKiQ==",-3551583383550188851,-7372705072019291631,-4408600107318499241,6338774110360508243>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s15epxr68v3my0","h5JLr8f7x+Z1jjBu76L0Oix26etYX9uyJZrPboUY1V8=",991149021573424981,-7157898028827677757,3651735673643634881,7847052231254492025>(),
         (String)com.yiyiaddon.m.b.a<"s1wmhj0h60ifxc","ePGE3EXly3UuWANVNnhMgWbjec9pmt86P3wdbvgASGvQPA==",1425848601518534232,-4288183259533832751,-5241692014860858864,8580839937771268136>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"sbwqm1gxoa05r","x8D+NCMPqJal2HfdiSHm/vvqP+Q84HP3elkXv9og0Zs=",186685977536076844,2545622009740416331,4966968901805024598,4307057777873744916>(),
         (String)com.yiyiaddon.m.b.a<"s1hwnqkvwfjaic","duc0OtwRnUpY2zHNNEspcMTx2EHpeNvJWXEa6nMSQjn0Z8sWxaQ=",-286968879682603817,-7929503837402905166,-2874923217097846625,-5860563846275209403>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s2grkky77q6mns","X5v4bsYiZQRJGutZ02+Di2T98TI0pNaF9iE4USbWERI=",3083189654412925798,-8952641734880829488,2896769284459838497,3383181017095230882>(),
         (String)com.yiyiaddon.m.b.a<"s31dvkuh56sfxi","PTUubBPAD3/jt81apEhrvyn0F4wxHnZAWKaA5zkof2aMVQ==",-5641492742902455279,-8733632961027590242,8348285534490087554,2267312385761730845>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s1b75zalsg19vq","mlSSSFqKqPtDv5uuxHIl8DRjHNWwVsdgXg5mdT4cr4g=",-188691562813215498,-7172966938097627392,1126337025617223403,-4685645075103260690>(),
         (String)com.yiyiaddon.m.b.a<"s1g2q4kpfak3bk","d54BtIjjXTdOriofijag7ZHqAPS5wDFIBrZaYXvBh9OmVX8E",-8968814954666652895,1212100932748368582,3710798831370637509,-5543485692075198086>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"sjrsncku92f9r","q7fHS+vJOMYdOxsITmBPsq0UymURo9Hl3nLKlq/WRsw=",-2750877950107218907,-194938837195463556,2527145666143696330,-6981515674750962909>(),
         (String)com.yiyiaddon.m.b.a<"s1ums7vp3pevft","IxZZxzhIY5yeKzsHZtB+rW+tZlWCq3fh7tj/HzJ5yFLdqw==",3028039035336302826,-8515826021523523928,6845682731718235661,-138167052828445795>()
      ),
      Map.entry(
         (String)com.yiyiaddon.m.b.a<"s28iye5gtx4j3l","bD85OTblLwweNBl3zaSUhrWH8qCK9kK/Fgri43+N418=",4352026453187124515,5323239309733444020,-3291681292226426154,5875310584520250357>(),
         (String)com.yiyiaddon.m.b.a<"s22a3zc6bg0zwg","qWdDTsDrch/6VLYVP2D/6p1muUyE0wbjfgeaQnR5M9A=",-5717516494470782634,7234187394820176632,6308468344097867479,3844718288732603137>()
      )
   );
   private static final Paint m = new Paint().setAntiAlias(true);
   private final com.yiyiaddon.l.e.a a;
   private final Consumer<com.yiyiaddon.h.d> n;
   private final List<com.yiyiaddon.h.d> ds = com.yiyiaddon.h.e.c();
   private final Map<String, n> bi = new LinkedHashMap<>();
   private final List<com.yiyiaddon.h.d> dt = new ArrayList<>();

   public e(com.yiyiaddon.l.e.a var1, Consumer<com.yiyiaddon.h.d> var2) {
      this.a = var1;
      this.n = var2;

      for (com.yiyiaddon.h.d var4 : this.ds) {
         this.bi.put(var4.s(), new n(var4::ar, var1x -> com.yiyiaddon.d.b.e.a(var4.s(), var1x)));
      }
   }

   @Override
   public String E() {
      return com.yiyiaddon.l.a.w(
         (String)com.yiyiaddon.m.b.a<"s83xf099d72m3","8KweApYM14INPrYWdZn04IW+lsnztwxGE+aCJM1Kyto=",-7671511526359731990,6107036418947642644,2388737909100701170,-6109709506828942494>(),
         (String)com.yiyiaddon.m.b.a<"s1mqqs6lhde2sp","L3P3GNZHsoiLF/lfV9dAmjqxaIp72GAidsafzQVeegCrjPYJ",141218989383230641,-918728135426589712,-8194520199526015125,-2607110162230711268>()
      );
   }

   @Override
   public String F() {
      return com.yiyiaddon.l.a.w(
         (String)com.yiyiaddon.m.b.a<"sqm7y2qvi14gr","B6/tK6hNwA4asqSKV4iEkHTUC8s1beDxhgbj7yILfv3momyN7pdrRYGPEKgIFfvnMH7RmYyI1PPvkJ4hoV4=",1825132326550368990,-5787656395538317792,4684144708455401975,1255426135245541907>(),
         (String)com.yiyiaddon.m.b.a<"s2wjdmzuxqfn61","oB+B8+UblisbCdv0fCoReMcq1HPkWOemQcOqhspMetgtYYjnyPOgQfadWAFQzFkItuD0KEc2192KzV0xTnfCF/PToCDv/PCi3mnEV75lcb2pdNa0L+p2M/Lw",7805622135185763944,6103242175271839887,-6744828536991992872,7391109408427623507>()
      );
   }

   @Override
   public float D() {
      return 18.0F + this.L() + 12.0F + 114.0F + 12.0F + this.O() + 12.0F + this.R() + 12.0F + 104.0F + 10.0F;
   }

   @Override
   public void a(float var1) {
      Iterator var2 = this.bi.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1xc5j9aq07gn6","mfbQBELO7vMU6PEjCt1Ow9pdptBoMYNnZfvvObnd0Jg=",-4698582641330855244,449571167635111439,-974257115339280996,743837442098353160>()) {
         case -1040432715:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"szmyivd082bt6","AYoGmFQAUn/tjycRUxDGg05UWSJFaJNHnO1HK886Kcs=",8059112156484181470,-5071251762204351784,-4271593628272918072,-3630234133947955206>()) {
                  case -1095443166:
                     n var3 = (n)var2.next();
                     var3.a(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s30te7wmxwqn92","0dLa+ODZkUgCVAEQdFt1soQb06+Xj6KhxzkwRqLe3GE=",-6074531487839268284,9007116148439926418,5930922890929470608,-7054520773480756374>()) {
                        case -438543565:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   private float L() {
      return 160.0F;
   }

   private float M() {
      return 30.0F + Math.max(1, Math.min(this.dt.size(), 6)) * 30.0F + 8.0F;
   }

   private float N() {
      return 30.0F + Math.max(1, Math.min(P().size(), 5)) * 28.0F + 26.0F + 8.0F;
   }

   private float O() {
      return Math.max(this.M(), this.N());
   }

   private float P() {
      return 30.0F + Math.max(1, Math.min(this.bK().size(), 8)) * 28.0F + 8.0F;
   }

   private float Q() {
      return 178.0F;
   }

   private float R() {
      return Math.max(this.P(), this.Q());
   }

   private float h(float var1, float var2) {
      return var1 + 18.0F - var2;
   }

   private float i(float var1, float var2) {
      return this.h(var1, var2) + this.L() + 12.0F;
   }

   private float j(float var1, float var2) {
      return this.i(var1, var2) + 114.0F + 12.0F;
   }

   private float k(float var1, float var2) {
      return this.j(var1, var2) + this.O() + 12.0F;
   }

   private float l(float var1, float var2) {
      return this.k(var1, var2) + this.R() + 12.0F;
   }

   private static float o(float var0) {
      return (var0 - 12.0F) / 2.0F;
   }

   private static float m(float var0, float var1) {
      return var0 + var1 - 14.0F - 8.0F - 44.0F;
   }

   private static void a(Canvas var0, float var1, float var2, float var3, float var4, boolean var5, float var6, com.yiyiaddon.l.i.c var7) {
      float var8 = com.yiyiaddon.l.b.j.h(var4);
      int var10000 = var7.uQ;
      int var10001 = var7.vc;
      float var10002;
      if (var5) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s2kgr1r793ipbu","RXNEHE0g8kCVSnOuH8OEVxc4d1Mb5PQVTli9abRGoDM=",-8309424574340854644,-7790155181787286039,4461929297059546781,1919361557637007716>()) {
            case 2100696896:
               var10002 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s29dbampeexzji","iBDfMNS4DwCsxsiFlRKV+7VTJCIBUPwH9+zNA580yuY=",-3492665536856397149,8303666864927196279,-1857176901496663704,5625333851340836001>()) {
                  case 244085175:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s31amqxyh6hn9s","p2GsoCJMxayExZRxu50CRSD5rCbcWZgEh0L93slfH/g=",3932910536322308188,-9171590050351507082,-3156790560629111411,-5869451742804176261>()) {
            case 89483240:
               break;
            default:
               throw null;
         }
      }

      int var9 = com.yiyiaddon.l.b.j.a(var10000, var10001, var10002);
      com.yiyiaddon.l.b.j.a(var0, var1, var2, var3, var4, var8, var9, 0.55F, var6);
      int var10006 = var7.uX;
      float var10010;
      if (var5) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s1z3tbw6o1z09m","C7fsOs0KTgoJ7YtJeS/x9RUoMoueME23aF7KQqPopWM=",8337323887351352976,3471147775844792682,3747908093623546449,-7372420786721726314>()) {
            case 1571151450:
               var10010 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s374n6umuxb2ch","uhS1EphOMr+8LuO5cEZLC4ulREwwXpjPzLtO4gqbqsk=",-4303680105385595088,6861232229165207773,-7981709385326865436,-1093601425221454525>()) {
                  case 176667359:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10010 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s2x3djcrtzk5t0","QLViDCjPsOLqg3R1og62AinfQM5XDHXFa8ig24ZGCvg=",-6863943438117970681,-1021701250344053370,-3993718218076692247,-7629298559902338741>()) {
            case -1706252319:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.b.j.c(var0, var1, var2, var3, var4, var8, var10006, var6, 0.06F + 0.14F * var10010);
   }

   private static void a(
      Canvas var0,
      com.yiyiaddon.h.d var1,
      String var2,
      int var3,
      String var4,
      float var5,
      float var6,
      float var7,
      float var8,
      com.yiyiaddon.l.i.c var9,
      float var10,
      float var11
   ) {
      boolean var12;
      float var14;
      float var15;
      String var10000;
      label87: {
         var12 = b(var10, var11, var5, var6, var7, 28.0F);
         float var13 = 22.0F;
         a(var0, var5, var6, var7, var13, var12, var8, var9);
         var14 = var6 + var13 / 2.0F;
         var15 = p.a(var0, var1.w(), var5 + 6.0F, var14, var8, var9);
         if (var2 != null) {
            label80:
            switch ((int)com.yiyiaddon.m.b.a<"sah17rzr0afga","R7r3Y0Ws/JgyAXdLhe/E5poeB93bUomQrqm2Fjf522A=",-7790078484351832845,2880092598157268875,2004897834253436112,-1532189407119484482>()) {
               case -1452449640:
                  if (!var2.isBlank()) {
                     var10000 = com.yiyiaddon.l.b.d.a(var2, var7 * 0.5F, 11.0F);
                     switch ((int)com.yiyiaddon.m.b.a<"s2mhgf9ms21r2j","/VKcAHUXRPxlRoASBPCSP2FEuRls/Thd3UbMHAYdOCg=",8702074890112887958,-1492521595504368263,-1057052723521498820,6573896923518682981>()) {
                        case 55427222:
                           break label87;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3gl4klh0c1g8q","1dUeLq0spdxsbiX0eYdOdTQr4w2iJ3M7PfYbFxHEPB8=",8034500573932916086,1854650829289891633,-3307165232021342803,5870807875629306263>()) {
                     case 933569620:
                        break label80;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10000 = (String)com.yiyiaddon.m.b.a<"s32g567204ilt9","UkBJq8tM4KJok7nsvAd2kbPseyMupvxkyXYy6w==",-705948314504226962,-2259136850606762614,636203735549940144,-8445967492709934357>();
         switch ((int)com.yiyiaddon.m.b.a<"s2e17zso1z21jl","bOg/eVpOr0kyxsN80E6lX70bxwE9vOo7LPp36d/93zc=",1188466072809424860,378553299128125419,-5498434263759279214,-2509165617365522874>()) {
            case 251289315:
               break;
            default:
               throw null;
         }
      }

      String var16 = var10000;
      float var19;
      if (var16.isEmpty()) {
         label69:
         switch ((int)com.yiyiaddon.m.b.a<"s1hjaps1meoza6","Yz7xvhx8bFtnf1Ci9OP+edAzyVygyJwu0qSgg2GuHbE=",-5660240583482876026,9008192385227957968,-2915430632940909174,7182140699239086930>()) {
            case 1403558402:
               var19 = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s6dxje683h0r0","zrWa67gfVGe+xEM/bRTWV6F32sQRjMrp9e2ikDkJ5fI=",347555010239399256,-8816061203816608569,-8138906926618013695,8771622722440894062>()) {
                  case 1062926370:
                     break label69;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var19 = com.yiyiaddon.l.g.a.b(var16, 11.0F);
         switch ((int)com.yiyiaddon.m.b.a<"s2n3bbqcar3ovl","pl3qG5tgRBSYvDijRtFieEFiJET3vnBMp29BXshum8E=",3260179019123345758,433941432232114604,3357742165758988770,9210179170311707458>()) {
            case 405429858:
               break;
            default:
               throw null;
         }
      }

      float var17 = var19;
      float var10001 = var5 + var7 - 8.0F - var17;
      float var10002;
      if (var17 > 0.0F) {
         label62:
         switch ((int)com.yiyiaddon.m.b.a<"s2kzyjt95l54hh","IEgwM7FBVsiWY0ew1N1oCC4LZ6ARj1331tEsekVuI1I=",-416777804286798492,-7737246486663686074,4206195942054006500,-3165014248818343628>()) {
            case 345414754:
               var10002 = 8.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s1uu88isui72tj","7s1F/vSGXgl/W82gRee2dLjdh501vQbYSKk0I6uz/2I=",-6187854143310201656,503651946526508918,-1850145715523222155,602758123796614973>()) {
                  case 9708235:
                     break label62;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s1zip7fvpaouqf","WL+jDrI9PFGOMIjYDN4R7oCQsqLFdQCUYnEn6jcGKtA=",-7732908851776463583,-4016703405379320415,-6580366034821790311,-1268886692951294789>()) {
            case -1550545796:
               break;
            default:
               throw null;
         }
      }

      float var18 = Math.max(24.0F, var10001 - var10002 - var15);
      com.yiyiaddon.l.g.a.c(
         var0, com.yiyiaddon.l.b.d.a(var1.m(), var18, 12.0F), var15, com.yiyiaddon.l.b.d.c(var14, 12.0F), 12.0F, com.yiyiaddon.l.b.j.a(var9.uT, var8)
      );
      if (var17 > 0.0F) {
         label57:
         switch ((int)com.yiyiaddon.m.b.a<"s2lt4z399df4mz","JH4WS2FZlC3rEnWPxHx6VnI7ubIFLKvv2q7bFSsO1Hs=",-8667654257064565366,378936201987558642,8188444231276083316,5461455193285008211>()) {
            case -740478520:
               com.yiyiaddon.l.g.a.b(var0, var16, var5 + var7 - 8.0F - var17, com.yiyiaddon.l.b.d.c(var14, 11.0F), 11.0F, com.yiyiaddon.l.b.j.a(var3, var8));
               switch ((int)com.yiyiaddon.m.b.a<"s58htoqe8omdw","hKHVG6KsZ1x19msd06BUaYvLZha9cGP+8fbw+C4UVPg=",3957021569011169314,8139728334046832068,-1781955151891768770,-3144898818183653748>()) {
                  case 1720386891:
                     break label57;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var12) {
         switch ((int)com.yiyiaddon.m.b.a<"s2cyyzsge0582w","5btMtS81e4h685NKZN8632IMxkqDXlbkUd+qvpWcO9g=",-5729466936516647919,-8761625534390136374,-1288755157735384221,91876683180357157>()) {
            case 130945670:
               if (var4 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s27x038vqkr2hx","da+e5FpGA18OaZ/r1EF7ohz8hmDov8H8tttXREWXqPA=",663791025051194069,8876609411583134393,6469606105634486964,4954359517403937997>()) {
                     case -1739652798:
                        if (!var4.isBlank()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s407c8yb0ctyy","GXmfw+Lo/pbdsCjpvDVRrAAr7yo4jHNmsowqFAeBlSI=",2363087268265500670,7701985839181233810,-4470836155716904895,-525613402176808938>()) {
                              case 237316692:
                                 com.yiyiaddon.l.g.j.c(var4, var10, var11);
                                 switch ((int)com.yiyiaddon.m.b.a<"s30haxl75exfhi","FVYstucAsOfghH35XPnuAO4vyhQNgUaj1/d5vC0hPEQ=",3581245223490808103,-90559591437116410,-4533266454470702695,7801502618911742306>()) {
                                    case -1665626137:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      com.yiyiaddon.l.i.c var10 = com.yiyiaddon.l.i.c.a();
      this.kb();
      this.c(var1, var2, this.h(var3, var7), var4, var6, var10);
      this.d(var1, var2, this.i(var3, var7), var4, var6, var10);
      float var11 = this.j(var3, var7);
      float var12 = o(var4);
      this.b(var1, var2, var11, var12, var6, var10, var8, var9);
      this.c(var1, var2 + var12 + 12.0F, var11, var12, var6, var10, var8, var9);
      float var13 = this.k(var3, var7);
      this.d(var1, var2, var13, var12, var6, var10, var8, var9);
      this.a(var1, var2 + var12 + 12.0F, var13, var12, this.R(), var6, var10);
      this.e(var1, var2, this.l(var3, var7), var4, var6, var10);
   }

   private void c(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6) {
      this.b(var1, var2, var3, var4, this.L(), var5, var6);
      boolean var7 = com.yiyiaddon.k.e.eX();
      boolean var8 = com.yiyiaddon.k.e.fz();
      boolean var9 = com.yiyiaddon.k.e.fA();
      int var10 = com.yiyiaddon.k.e.dG();
      int var11 = com.yiyiaddon.k.e.dp();
      String var12 = com.yiyiaddon.k.e.fw();
      this.a(var1, var2, var3, var4, var5, var6, var7);
      int var13 = com.yiyiaddon.l.b.j.a(var6.uU, var5);
      int var14 = com.yiyiaddon.l.b.j.a(var6.uT, var5);
      int var15 = com.yiyiaddon.l.b.j.a(2278750, var5);
      int var16 = com.yiyiaddon.l.b.j.a(15680580, var5);
      int var17 = com.yiyiaddon.l.b.j.a(var6.uS, var5);
      String[] var18 = new String[]{
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"snhx6ct9pm6mk","WRN41defjIG1QXKgCvgXgSxa91bDyQMAxAPqc7gh7G9xS2/Z",-6554924749351003435,4675871314212782748,-1797538134181721696,612037753831413577>(),
            (String)com.yiyiaddon.m.b.a<"s1kaxs452tvm5p","reMzhU4m5zFqqe5YYXqBwgsPEKhS7cfNL5ZQ2SYjHHJuKp85",-8978218250089579207,-3445543995440065245,-1037248816527574095,354090148298542210>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s2wzzddh274a1w","uut2RCpiynAYte4PNFFC6K8Q0Wtcvt/ZBsiVYtsT0QFHH2N4",7176258682139184004,-580599655174047926,-7331876919715052865,-244366267914646063>(),
            (String)com.yiyiaddon.m.b.a<"s3vj35259f5nzn","49vQU86ebj+WS27+eafvT9ZyQviqjhzTBCs9Yp0Ad6ymY3NKtGs=",1515161314320214903,3438224743146365142,8674113864673095813,6869927787130207734>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s21wp4q6bg5te9","h//jXndYqzoWsiyouzwvdFVS+ifgJuV32qnfhlnkLGr3J1hY",5291954378941592416,-6187063721603047540,5190597166850562970,-3686067210679656348>(),
            (String)com.yiyiaddon.m.b.a<"s3a9ka3jnh8kix","NfemruG9Xt5EZv4pJf1J5RU56PcO2cC8Ub1BNWzM28pHQ1brwAxEasXZ",-4933202464201684594,-1030780623821422437,-6476093507000475737,5000356738467407312>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s2lfk7ec6vvast","Vooneu411on6n2RhR54Ug/DR7Vf/IINQ9d3+ASwWqA06F91J",4759517841096425955,8610137611659073043,-7074541987404509098,-6790442375754119093>(),
            (String)com.yiyiaddon.m.b.a<"s1kx4d88qkf0hr","WYmVqswHosv8njHTRI86gz7oJs+n4/Sjau3oW1ALQ1kslm2EkW26Ng==",-8771836753100658761,-7297290701612157422,-8437240511084801466,-4138092691074473093>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s2qxcw1mb79tpq","CvAOpu0HxxTX7lMJK3fjJeEWy/XQ25TEYtNa8EuEMuc=",1077844255834901123,5396567873646553402,8038010766742505656,4438149195087537456>(),
            (String)com.yiyiaddon.m.b.a<"s2qxcw1mb79tpq","CvAOpu0HxxTX7lMJK3fjJeEWy/XQ25TEYtNa8EuEMuc=",1077844255834901123,5396567873646553402,8038010766742505656,4438149195087537456>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"sp3qm5rdsesuc","1YweplEg5W116vH6NOm4HiRATG+YsS8f+t2F0bBebsStHPZN",-938967012123420505,-707122070643247637,7341179425341251779,-6345777437949013885>(),
            (String)com.yiyiaddon.m.b.a<"s1gsllim0aglc2","y+li83pbGkYdRgV/HPKI4bv76675Uy/bjdhDPoLcxsX6hkR875bV4BPbJryebQ==",-1687410694896591344,6303842982900872218,-5160764471694349283,-536427067193220470>()
         )
      };
      String[] var10000 = new String[6];
      String var10003;
      if (var10 > 0) {
         label88:
         switch ((int)com.yiyiaddon.m.b.a<"sfkwxbxfraj7j","nif1u/fG7FaQKTRuPRIwQOQcBRtbUqsNYcY3M9PFNG4=",-5466314824467385421,8405236789631953242,-7804562252253271394,5332752862637846178>()) {
            case 1177254751:
               var10003 = var10 + "";
               switch ((int)com.yiyiaddon.m.b.a<"s1rbtadn7qw36z","cBjpDTdeWpwH7jl/7j+7L9syk1cxL2Hjh5XFCDMRpBw=",9106163968389858976,-2496178382662038098,5203462963692619409,3850202235496241775>()) {
                  case 1206146902:
                     break label88;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10003 = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
         switch ((int)com.yiyiaddon.m.b.a<"s29451k0ylms4q","Ug9tI4tHl75eBKgWyyr50L/z7vb8MGfZXMG9T6XKCT0=",24282451804500966,6173917831381282936,7736601489136390123,-4716328196739890044>()) {
            case 1629477839:
               break;
            default:
               throw null;
         }
      }

      var10000[0] = var10003;
      if (var11 > 0) {
         label81:
         switch ((int)com.yiyiaddon.m.b.a<"s2xmw622jhajif","Sgng2toRRaqk0KJ2yL2ZD1obnIE7JTcY3hH2GFCmfJc=",4874139020982225103,-8987705109975124896,1307710146160836982,-4172297118552181735>()) {
            case -1174726411:
               var10003 = String.format(
                  Locale.ROOT,
                  (String)com.yiyiaddon.m.b.a<"sqx5n3d2kdiv0","f3snKGepVGE77492CFQKJsu0LKraZ/SgLIKs8X7dPeMo+Q==",3009351366143036595,8542387036487163755,3665986563754206340,-5158802345401971312>(),
                  var11
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3gcx551e0a2po","3ylUTifAJHXaJllDA0f+ykOQ08rTttImmLtqHVNZ8o4=",-5488111011644718389,959033498764546291,5477638972012144572,-6258924848503968164>()) {
                  case 346486568:
                     break label81;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10003 = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
         switch ((int)com.yiyiaddon.m.b.a<"s1t11zfyy7p8xk","DlKNwkskbnffKI3A6vPxPZ5lrpL+2r3zhs3/35L01fo=",3565066717185214777,-1261364120633512796,-5134002571477089452,8316125167693835201>()) {
            case 116821227:
               break;
            default:
               throw null;
         }
      }

      var10000[1] = var10003;
      if (var8) {
         label75:
         switch ((int)com.yiyiaddon.m.b.a<"s3tybfcm8ygyga","lZzCMZ2YZa/csxlzTl9lRKzOjg2OAhacv2SNI/9VFgk=",8418788467651012624,117593697403098652,696132193430454401,-4284735405450538316>()) {
            case 618398118:
               var10003 = com.yiyiaddon.l.a.w(
                  (String)com.yiyiaddon.m.b.a<"slcowebac0lo5","67bBUYmI1a5XRLuUXP+ZNb9duJHDZYl4ItQy6jrHHBo=",-2109145579702505089,-811909257929337840,8628577668003084839,5342405359440305622>(),
                  (String)com.yiyiaddon.m.b.a<"s2v9i4yjreebsm","N/Rvu964i5rgZ3vAz+JpSf8Xs+Z40C0ubFvMXbVuln8fhImeTkpA1A==",621388634083557962,-7758166729260828355,1599590999584857114,-8346094346380931377>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"sq1sf1xxpi4u6","uyFfzyVj8Bh9ALZYbh876GL2H57llVdM9F7t5Y+PZkI=",-7500898319368534936,5509878697765926551,-6739207809610408158,-3908281655669145505>()) {
                  case -979254975:
                     break label75;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10003 = com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s14rdxv06qhvjf","VFWQjkwW0BQk83ZQmNPpdFa6mBm+DLlnvIrRUZZsdCw=",-172478351870682711,459391124441842057,6086272406215040820,4579327266795687657>(),
            (String)com.yiyiaddon.m.b.a<"s3ko9n2hkd6dm7","Zuz/rYn+yRitOv+wVZPBD5XM1O8JVDs9CGywp+M45DxmXScJpAMMHNIz",6740218237899364131,7718252388266862908,-5470144631907185517,-7426943939807394694>()
         );
         switch ((int)com.yiyiaddon.m.b.a<"scpmgiqq2u7lu","XWWUXZlyxJY+m5/yaWtGWXY4n3kP61ga33vMyGs6I5U=",5513667132112654926,2404704635591004922,-3622744843919184824,2786910164813551512>()) {
            case 1311167967:
               break;
            default:
               throw null;
         }
      }

      label98: {
         var10000[2] = var10003;
         var10000[3] = cm(com.yiyiaddon.k.e.fx());
         if (var12 != null) {
            label71:
            switch ((int)com.yiyiaddon.m.b.a<"s283lsqn2iq3sl","Oo/wUNFiDyNGiBblR4icUiLe8rmV3W+6Wyqaa179qNY=",2871513587865011303,-6888360332122556024,5778173623798603420,-7281542783839133421>()) {
               case -731555863:
                  if (!var12.isBlank()) {
                     var10003 = var12;
                     switch ((int)com.yiyiaddon.m.b.a<"s2exj8otgaltbe","YueRVsVjEqBNJ/o6wZL9wS45n4kAJnOLtvHbBq378nY=",-5362073646065036627,5911729181220044819,-4783802106662110670,4981076903079962326>()) {
                        case -1796873975:
                           break label98;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2clng547wtmjm","POELij/rbAcgGD4W019+SLBF9GfSZNrKma4ho0lJzpA=",2813724621232000903,-3819367770999844459,-1440451677571005405,-3215896914459633749>()) {
                     case -932093565:
                        break label71;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10003 = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
         switch ((int)com.yiyiaddon.m.b.a<"snixseuz0hdb7","ZIshxIUu2m1YaFQrcFqI9BKBIrtbr1FlxIsRqQyRFJs=",-1159631422584558321,6167033512995473078,-5460667194324312944,3846090189570421211>()) {
            case -903600309:
               break;
            default:
               throw null;
         }
      }

      var10000[4] = var10003;
      var10000[5] = gL();
      String[] var19 = var10000;
      int[] var23 = new int[]{var14, var14, 0, 0, 0, 0};
      int var27;
      if (var8) {
         label60:
         switch ((int)com.yiyiaddon.m.b.a<"s316ort5vqb8fe","clYVNtePEwYYmEbG8TN3loW23+vbCldbvikbIS6vGBU=",7104378777890451105,8405361763809929250,-3545750198320225278,-5355965776361569038>()) {
            case 168470959:
               var27 = var15;
               switch ((int)com.yiyiaddon.m.b.a<"s3orrp8u5t7gx6","YJfAwTbT7yivvkI+U0P7JgBbTu0mJp6gnqt3+lyIb3g=",-3313232571018319419,8110371814381754944,-6321647267877598150,-2067041274552575794>()) {
                  case 455053239:
                     break label60;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var27 = var16;
         switch ((int)com.yiyiaddon.m.b.a<"s2kf2nngde4a0h","8LVK1PRoogPK6c5E1ACpmQ74ES10MAdu5UrvD1nxxzo=",-8966302421496897783,-9086628149209165839,1590204657956322164,-3376595504535823811>()) {
            case -930791033:
               break;
            default:
               throw null;
         }
      }

      var23[2] = var27;
      var23[3] = var14;
      int var28;
      if (var9) {
         label53:
         switch ((int)com.yiyiaddon.m.b.a<"s184yza4ilji4g","lP/Q+xC/PnknXGfPurd9eUszdhZfBKykjvPCiV1+eNs=",-7328519986052164155,5192611015806836555,-7863571050455414281,2504081385665047048>()) {
            case -54902859:
               var28 = var17;
               switch ((int)com.yiyiaddon.m.b.a<"s2yhs0bsilqrjt","+F9s3x18P7Po9bCa4LKfVkLtWzTn4CBh71bRwahDnp0=",567105283694099964,999504651334766207,-2981022758840204920,-715832011756981267>()) {
                  case -801438600:
                     break label53;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var28 = var16;
         switch ((int)com.yiyiaddon.m.b.a<"s24apkypsw16vb","UThTwXnInvO0THYsj6unia1dti0w4kvmm6fhLDwY0GA=",-6690610585743282840,-1539305521697842596,-4780656226976460358,-5090775084674330571>()) {
            case -1818953388:
               break;
            default:
               throw null;
         }
      }

      var23[4] = var28;
      var23[5] = var14;
      int[] var20 = var23;
      float var21 = var4 - 28.0F;
      float var22 = var3 + 46.0F + 12.0F;
      this.a(var1, var2 + 14.0F, var22, var21, 3, 0, var18, var19, var20, var13);
      this.a(var1, var2 + 14.0F, var22 + 44.0F, var21, 3, 3, var18, var19, var20, var13);
   }

   private void a(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6, boolean var7) {
      int var8 = com.yiyiaddon.l.b.j.a(var6.uT, var5);
      int var9 = com.yiyiaddon.l.b.j.a(var6.uU, var5);
      int var10000;
      if (var7) {
         label60:
         switch ((int)com.yiyiaddon.m.b.a<"s2tigx884df0gj","T5alM8esRk7yUU/0wJ6uXbQzdWyLnrnwoD5/w53vRUw=",-3767130985443531385,5330161783577966792,2290929251892749758,6528237261424558491>()) {
            case 712332135:
               var10000 = 2278750;
               switch ((int)com.yiyiaddon.m.b.a<"s3ipop30ujpjj","F5dwV//IQ4bzsfAnlBAp1YEHp0Cg8UdSKrYOzUb/tW4=",8423899773697964464,1110552218651406562,1895428212586008197,-4928065613931685811>()) {
                  case 2063213218:
                     break label60;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = 15680580;
         switch ((int)com.yiyiaddon.m.b.a<"s3303asfnok2q5","cH0hONY9sPgyAm4ZWH94ix8I30xNnxbDi0D0AxjiSCI=",3813296803257919263,-5936576780699938749,5467849654162172448,5271247973579011261>()) {
            case 280733337:
               break;
            default:
               throw null;
         }
      }

      int var10 = com.yiyiaddon.l.b.j.a(var10000, var5);
      float var11 = var2 + 14.0F;
      float var12 = var3 + 7.0F;
      com.yiyiaddon.l.g.e.a(var1, b(), var11, var12, 32.0F);
      String var20;
      if (var7) {
         label54:
         switch ((int)com.yiyiaddon.m.b.a<"s1nqtfg05xh1zp","5mv8RsjkuHFn4SlaOtzNVGQXqoXxYPHrt9vjSa8uPGo=",-9118971034828263057,2547629407011004768,3230144000217310413,97734233202305190>()) {
            case 1836310205:
               var20 = com.yiyiaddon.l.a.w(
                  (String)com.yiyiaddon.m.b.a<"s1m4dncds57004","d92lIficSkKLbV7EaNDbHfxGntSaRgZIWSpme3nB7mg=",966150045512806274,-8789897032911613853,1666986824562391027,2272237506068424377>(),
                  (String)com.yiyiaddon.m.b.a<"s3f661s887hfva","KErMi/cjki98XyyPsYzAEXx1TbmQNNpbVB9SOkox4Ri6OLBScOSfEe8L",-6679922778002123317,-4534961964939111054,-2454707791780015418,1975244545500439204>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"sgjrj3d452eyh","sy9zHPyn9b2WaDCEsNqstjjubdu3OC6rgDgtHRQzaMs=",-2054950971365685727,-4519945631796204968,-1777975110807261154,-7782710510358589401>()) {
                  case 1240259984:
                     break label54;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var20 = com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"sui6vdc72itx1","rAtGCfPS5Nnj5UTizGfhhTZ3HMZWdB47mPACGH4rEkQ=",5730493925598804730,-8527321590564780323,-8564224715837568619,9093956196644136021>(),
            (String)com.yiyiaddon.m.b.a<"s3ko9n2hkd6dm7","Zuz/rYn+yRitOv+wVZPBD5XM1O8JVDs9CGywp+M45DxmXScJpAMMHNIz",6740218237899364131,7718252388266862908,-5470144631907185517,-7426943939807394694>()
         );
         switch ((int)com.yiyiaddon.m.b.a<"s1ioh9orlljhcn","i6i8nOqrL5fjHSTcqMlPNQ51J35EFwBUCkEnGeT5R7Y=",4309928253798593661,-571633593558174126,-4698853132852817358,546780072690354329>()) {
            case -14922231:
               break;
            default:
               throw null;
         }
      }

      String var13;
      float var15;
      float var16;
      float var17;
      String var10001;
      label70: {
         var13 = var20;
         float var14 = com.yiyiaddon.l.g.a.b(var13, 11.0F);
         var15 = var2 + var4 - 14.0F - var14;
         var16 = var11 + 32.0F + 10.0F;
         var17 = Math.max(60.0F, var15 - 10.0F - var16);
         String var18 = com.yiyiaddon.i.b.a();
         if (var18 != null) {
            label50:
            switch ((int)com.yiyiaddon.m.b.a<"s1yqm38chldqq5","deWe/tEoqWjlrWMSP5EHHLbfQwXUNczM9v8udSeDd5w=",8859819679499926,-7581104250570750212,7528291688666568022,-2920977362991558395>()) {
               case -1575522735:
                  if (!var18.isBlank()) {
                     var10001 = var18;
                     switch ((int)com.yiyiaddon.m.b.a<"s26s5oslu74ma4","FneCWlsDzMisW5ZX66RYIkPnRfHWPw/WTYQ4UY/d24s=",6731164182812894614,4459378487076197504,-165393059913423983,-6762337237687199244>()) {
                        case 1802083619:
                           break label70;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s29gqy4tm06mv5","c7F6FrVP+zjk70D1nzxoKhGO2BqCdtY6LvuyzOsPefE=",2393068690101593486,-4505146727457766644,6725106519687848739,1280139857814896882>()) {
                     case 598883161:
                        break label50;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10001 = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
         switch ((int)com.yiyiaddon.m.b.a<"sognd6phqwxs3","A0ufgFxAB6ZP4aso3qqXLX4B8NhibtmWVogdVPaTW+U=",1816925179635949963,-4974449145793100380,-4603704509820495486,-4847031714538698023>()) {
            case 1558627201:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.g.a.c(var1, com.yiyiaddon.l.b.d.a(var10001, var17, 13.0F), var16, com.yiyiaddon.l.b.d.c(var3 + 8.0F, 13.0F), 13.0F, var8);
      String var19 = com.yiyiaddon.i.b.gg();
      if (var19 == null) {
         label39:
         switch ((int)com.yiyiaddon.m.b.a<"s2t69rkw6335ny","3iGvIPruRmyKVcjZyP2g4Q3rTL7CtJt26ZeJkiozBgo=",-6152572242967723238,-3527108403799003012,-7605641017097215539,-7315073361636773792>()) {
            case -1052254575:
               var10001 = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
               switch ((int)com.yiyiaddon.m.b.a<"s2hxry2cjc06b9","4+2awHIhFQshe8nwTNZlFzCgZ4ST6uptnmWMLxwu+vQ=",2591842935771533070,-2415887570712581482,7129696202112942500,-3893102305388227915>()) {
                  case 1022800200:
                     break label39;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var19;
         switch ((int)com.yiyiaddon.m.b.a<"sbqjka6w9vvuc","BDxnSAiu1yKC3qE6R2jfeM+W8KA19yaN2keHjCoAZMw=",-1120710357564388150,-6349906544274266626,-5963701163639459653,-3736202613609032769>()) {
            case 682453257:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.g.a.b(var1, com.yiyiaddon.l.b.d.a(var10001 + "", var17, 10.0F), var16, com.yiyiaddon.l.b.d.c(var3 + 24.0F, 10.0F), 10.0F, var9);
      com.yiyiaddon.l.g.a.b(var1, var13, var15, com.yiyiaddon.l.b.d.c(var3 + 17.5F, 11.0F), 11.0F, var10);
   }

   private static PlayerSkin b() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1udhejwvuh3m5","4Kv17ANfHEfB5iN7DTgbDYKkWgJohHxkql634h/Ggrg=",7245418466092426258,-2930043560638970395,2284002276353993983,-5406048056885535136>()) {
            case 1683384476:
               if (var0.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3h93qf4yitdct","weF0Z7N3ws9Kw7wdyw+E14C8SZmOatJM+mcTaSrPGfI=",2123368276805063363,5170041712895100189,-1547069887071051802,-8645075247329608907>()) {
                     case -494975944:
                        PlayerSkin var1 = var0.player.getSkin();
                        if (var1 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sispenp9vlkb4","MQp3YkjF2cJZwvCUX+iy5K5+nFevT1zBC52U/Svhcfw=",-2219745622828750930,-5749204091508210141,3607624841722123039,-1985069627074976608>()) {
                              case 1615484906:
                                 return var1;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      UUID var2 = com.yiyiaddon.i.b.c();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s16lexjgw47m2","eS9DjIcDI55W6b8QfuuHtfx7pkiZLzK9S41MKfXLW4o=",7932682973089586154,-8747568786346567296,1344725358604229887,5535820550840646477>()) {
            case -496506938:
               PlayerSkin var10000 = DefaultPlayerSkin.getDefaultSkin();
               switch ((int)com.yiyiaddon.m.b.a<"s1tfl1petmrke4","TguRt6HkiN5shPr2FyYPEh+CyQX/6cbzIQhbxhwMUcA=",1362929077643961795,4752484583399108530,-944983350863379276,-4520996651400476964>()) {
                  case 1023048774:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         PlayerSkin var3 = DefaultPlayerSkin.get(var2);
         switch ((int)com.yiyiaddon.m.b.a<"s1ycayktclvgab","RZ/0dS3/cMCf1bdgpLEgEEpsosOUr6bKE/cycEL43jg=",4626211694105880292,2183762711052510358,1025276529920911469,-7950199760410052073>()) {
            case 1239696144:
               return var3;
            default:
               throw null;
         }
      }
   }

   private void d(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6) {
      this.b(var1, var2, var3, var4, 114.0F, var5, var6);
      int var7 = com.yiyiaddon.l.b.j.a(var6.uU, var5);
      int var8 = com.yiyiaddon.l.b.j.a(var6.uT, var5);
      int var9 = com.yiyiaddon.l.b.j.a(2278750, var5);
      int var10 = com.yiyiaddon.l.b.j.a(15680580, var5);
      boolean var11 = com.yiyiaddon.k.e.c.fr();
      String[] var12 = new String[]{
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s3nwsu87xqnxnm","tulIR0ZyKuXbKuZsqgLyYnrgIu18zkptTklUeWJn3BKSXg==",691453103078031274,-3544429680280797543,3147739065760508142,7546329507578726422>(),
            (String)com.yiyiaddon.m.b.a<"sfvz4s0j9fts9","e5LKiOt/jVW7fTgohL5kjubw3CdZHUnZupRvGt9Sxa4WFJYfQVY1GQ==",-6106248391827874506,2467958293654397329,9057604245769162375,-9047566574160570972>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s39zvymb7ircox","4B2e7EX9qcPFSoKjleNHMX8RMD3pmf8ZQ1XNj1rubaA=",5685083396187714803,8247872535787620964,1500052327120207638,-1776837941047803059>(),
            (String)com.yiyiaddon.m.b.a<"sxnbwqm1argrk","qK/c82DuKnNuRi4cAhNu+TJ+Ko29o6AEakcIiIt312CA1GoFvLbvTg52RMsOXg==",-793632006182744033,6650215072980348420,2207343282270580307,712268621612821199>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s6azewnyel0t","ZGEVDXcwAJ+5yNPKPVOizxwhDVeJYftzW990Rx5oXe0=",-498222724551241155,-7070576965819454454,-3104715223989004106,-959056580663443670>(),
            (String)com.yiyiaddon.m.b.a<"s214o6mqsnqkrj","4N1J1aXFiBF8IEhTDfmuaeQR6OjQaFmchqe6j9khqe4DpziwpAXKXxrCNcw=",1313006068321069331,6099938218948842800,3184787693368597966,-7599526214311224415>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s3sx91ge9300mk","xNGxo/s/5JThUDXxY8ukkwTUPAIlZ6CpXAikaxT/o2Y=",-6849138406139027772,7912503969428216428,-1967481540532168141,2726013479136957007>(),
            (String)com.yiyiaddon.m.b.a<"s2qkfpvvm04c3x","aq3A4XwMw9BNcLT/R2SrUkrJC9rxQdRSNj0gD2OKz5cSbQ==",7993602938638309291,1773371987975509562,-7484254793311375150,614192584669522846>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s2gwlgqpafj5rl","9TwsN8cT2SnjB3v4Xgv93s67rKctr4YkqUl9fFRpNuo=",2148802093780850332,4600045698062960618,-2027391668125564335,2769657365401737506>(),
            (String)com.yiyiaddon.m.b.a<"s1ngiizp4cy8fv","479Q/h8zll440GlVwHMqO5GdwLstS+SLpHvns1yOID23heZ4",-2013651691672082318,7366161264157478059,-1276277220229474585,-5119020044974095223>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s2b9u0r5nxnstd","DRuHjQB8/kC3x4dBYywC/O5LTeocXG3ckGfqJfOdF1be6uVd",-8323118520408497133,-7997232106308735267,2093190686177154800,2862631408928592291>(),
            (String)com.yiyiaddon.m.b.a<"s3vao294tvfpu","wQ6L/Y2nJIKXNWdfOiFS2ptkxFP/tvw98hYGGkloc2kzjpPUfFBFUQXR",3114430340718009465,7010405338146409818,574702852030143339,634466339707367646>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s2lzustid3voe6","0/8UtzohUyh6yhFKgyY6VBhw/86x0mWh3MkyDEgeyhjhQ3I1D2I=",7221827926182108361,3702236741988023076,-3335561359821192164,7461277323236968979>(),
            (String)com.yiyiaddon.m.b.a<"sxy87d6je11w7","bBcgmEp0hfjfqb4IeKsNw+INlc4IXxsy6pJkKoGO7SFoIV3xURHpKh3GWmcaH+kqb4UHv7FztsUQUyoT",-2943242874840028924,7109009621511496584,-7962739566847591513,1322745692833275072>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s19q2pqt3roxct","slKHklxyGELwIGnyFdy2ULRAF9zHfxMMXy6fCBulTss=",-8603412539441104189,-7676583483195484952,-5536013208928008022,1783466165823343364>(),
            (String)com.yiyiaddon.m.b.a<"s380z2uygxke7h","Vjjf/WoZ1H0l3AxW3WXzKin6HIsGJ2GkXxpdGrX5eGI3GACyTb5x05LC",9084726443705284557,2667501200641957018,2416046680358391580,-5773316055990176074>()
         )
      };
      String[] var10000 = new String[]{gF(), gG(), gH(), gI(), gJ(), gK(), null, null};
      String var10003;
      if (var11) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"sdnc002avz7bs","sMaREoWMsJUm9fsSyjdJtIb8rXPtx0+Dv6OFSrdcpHM=",-3721723418955747203,-1718887789861281509,-5971717960204768826,-1337103260064595056>()) {
            case -186391529:
               var10003 = com.yiyiaddon.l.a.w(
                  (String)com.yiyiaddon.m.b.a<"s2tzioo1tegcqs","eNuiK0yNhUypXaE7EQVk5LcsDF6Pi2YnLnExm5hNU85HBg==",-8616887542451418398,-2251290524667417956,-1010713018958910451,-6525779613714460186>(),
                  (String)com.yiyiaddon.m.b.a<"s2nn5xsomrw0by","KSFq4dRJubyFLRFu3QmMop6cUl+u8AGK87Jv7cbt7dg1HnKI+xE=",3295135735472242026,158031398951664233,1529985801970059209,-8577969569476307855>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"sexcw9uim2jz9","MKhjdYM+KKNws6hzPVXefqT2Sy65Fkr2gM0pVAK0088=",-3484672978076762958,-3053902395484067540,-6892916895853619455,-7528468349396337071>()) {
                  case -552058210:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10003 = com.yiyiaddon.k.e.c.a().h();
         switch ((int)com.yiyiaddon.m.b.a<"s34ztwuk2hteyd","8oh6ztGAU17MTGDcIgZa5+7+NfXxzbW1NCLsnSk52vw=",-7209048229185569033,-9102162728414181549,-2900024629040720378,-4604574287925950923>()) {
            case -416811975:
               break;
            default:
               throw null;
         }
      }

      var10000[6] = var10003;
      var10000[7] = "" + com.yiyiaddon.d.b.e.e().size() + this.ds.size();
      String[] var13 = var10000;
      int[] var16 = new int[]{var8, var8, var8, var8, var8, var8, 0, 0};
      int var17;
      if (var11) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s17hcw155eed26","gfodwGwADbJFpatbN3tviYl+ogiQfFk/Y3weKLW9kK4=",944897633726168475,798632896286204236,-8219044733181305086,-6676795042358293219>()) {
            case -438861552:
               var17 = var9;
               switch ((int)com.yiyiaddon.m.b.a<"s3spgv1mgee5bq","XvkrikYW0oluN/wrWkE0DHvlle/MeMDyhkaqyX1ncAs=",4275504536340885679,7285644701862779989,-4014867814456952239,-2598854436061792537>()) {
                  case 1596956513:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var17 = var10;
         switch ((int)com.yiyiaddon.m.b.a<"s3mwrhil1o470m","bUrwGAL5gxWoCKKFZT7kTqU6ErI3VEZ7g4VGswRKZFE=",-3803085691297601489,4909981544911333082,-1709360447263518691,8171196589885149701>()) {
            case -162664388:
               break;
            default:
               throw null;
         }
      }

      var16[6] = var17;
      var16[7] = var8;
      int[] var14 = var16;
      float var15 = var4 - 28.0F;
      this.a(var1, var2 + 14.0F, var3 + 12.0F, var15, 4, 0, var12, var13, var14, var7);
      this.a(var1, var2 + 14.0F, var3 + 12.0F + 44.0F, var15, 4, 4, var12, var13, var14, var7);
   }

   private void b(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6, float var7, float var8) {
      this.b(var1, var2, var3, var4, this.M(), var5, var6);
      float var9 = com.yiyiaddon.l.b.d.c(var3 + 16.0F, 13.0F);
      com.yiyiaddon.l.g.a.c(
         var1,
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s23u0p9xg1hb3d","mEZToD1LRDTG8r2k1vHBLmhI4FtOm49xH8AAxmEJflwPTLi8",-6484396541029346882,329177044956692245,-7698567825329824122,1166779047417359744>(),
            (String)com.yiyiaddon.m.b.a<"s2qtldpqh3q6xb","KXoM4gudoI8uV9DI7cb4uJ8PnJKLnfsvC3VxxAPRY9rpd821QpVdxJwqh65HSg==",-3388083703910751035,6148800349837744877,1966126388346881148,2380501893095179459>()
         ),
         var2 + 14.0F,
         var9,
         13.0F,
         com.yiyiaddon.l.b.j.a(var6.uT, var5)
      );
      String var10000;
      if (this.dt.isEmpty()) {
         label76:
         switch ((int)com.yiyiaddon.m.b.a<"s30kp1tdfxtfum","BtAJWUwC9nyOK+AIeLYjpsLy/RJgS8NpBE5xMni9sRM=",2603157611447886153,3977927476437602537,-541090240876099400,-7534835436756545259>()) {
            case 1798382445:
               var10000 = com.yiyiaddon.l.a.w(
                  (String)com.yiyiaddon.m.b.a<"s1woj5xhu3ns8r","Hg87Aik2GR4cbBkkMeLSfYaM5qF7McvvCnRA2vBllqEClQ==",-9151839389786841206,-7601409127880336093,7493981043600702564,-396176370170624428>(),
                  (String)com.yiyiaddon.m.b.a<"s100t1h05vlz1l","j14G7rs6/TT0I1WzYUT73RpfnYx/v+OO2IdwACtuJl4D/P7J",-1002739967907929073,7295904659254668315,-9210471283951595582,8344280390235656586>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3xpdg4bnj0t9","JmJcTItstDNjjP5gi7FqmMb4mqv80I/jIogL8srgC0s=",881920196516977398,-1034756513883031910,274846527177163453,2507088283763260986>()) {
                  case -1810851204:
                     break label76;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.dt.size()
            + com.yiyiaddon.l.a.w(
               (String)com.yiyiaddon.m.b.a<"s2ow350333d5a5","KccoibL/ibKoNLmO0fYEp9vHeLH6OnrG6bAJoNp6",9207786287140404578,8989226481637077675,5665862655169683255,-8768330449716250017>(),
               (String)com.yiyiaddon.m.b.a<"s3vq8v2s60d82r","QDQD86b+iX7yf1pjIXY3/oKPsDA1gC2B6FLF6+DtR70BEttdNwc=",-6684994778896857191,1402219455159700334,7869406007871866013,2957800944537470261>()
            );
         switch ((int)com.yiyiaddon.m.b.a<"s18gv6tg5085xq","aYh2fHoxy3fYfcDJ5kv3YEP6LO2KGdv36HD/olDo9R4=",-9109935068625591249,633481737313576687,1516794347250165110,-2502661519467871028>()) {
            case -1915914455:
               break;
            default:
               throw null;
         }
      }

      String var10 = var10000;
      com.yiyiaddon.l.g.a.b(var1, var10, var2 + var4 - 14.0F - com.yiyiaddon.l.g.a.b(var10, 11.0F), var9, 11.0F, com.yiyiaddon.l.b.j.a(var6.uU, var5));
      if (this.dt.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s9l8pyqxa3in1","aS1IoZVCgFPgv6261kte8hNgewjeu60bc3ge4uFP2WA=",4425149215724186293,8479105769076093196,-6308050060011916040,-1246525216324072717>()) {
            case 302854195:
               int var23 = com.yiyiaddon.l.b.j.a(var6.va, var5);
               com.yiyiaddon.l.g.a.b(
                  var1,
                  com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"sv0fteyw08ylo","ZQ17eB/xDbqEEON3M0Qx7PXdyERtcdyZ6v9Un1rOyUgAZYcA7MNjwKEphpZIYRgWSQlvPev7",-548406767212132314,-2931255944356480605,8697512212848500235,3418923003420356770>(),
                     (String)com.yiyiaddon.m.b.a<"s3k0d5vdmiwjem","B1w4ew0RvbxDW0aF8ZGw6PIKI8AN6jWlh5lGaxiioPqA9mzqq2fYtNVpapKVetx9KxH6VaML2m9FWR9VBtJgKNMMKfm34PnZoMuN8W8rI98sKpoG2FtryQ==",8839004166231857639,-1523003381400273248,-6321752360399812842,7202042919088906688>()
                  ),
                  var2 + 14.0F,
                  com.yiyiaddon.l.b.d.c(var3 + 30.0F + 10.0F, 11.0F),
                  11.0F,
                  var23
               );
               com.yiyiaddon.l.g.a.b(
                  var1,
                  com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s32e20ia95cwyv","cw5sBqDKvYwbyiZD7yp/+UjT+1PTWo3bOSWROp6QY9BKb9mca+m+Rqh4NIbngLV/xQnj7sBo",-3905821573440458414,-8933975206487020561,-2758023132677905648,305822243995247325>(),
                     (String)com.yiyiaddon.m.b.a<"s2d4mshopvb0s9","0/kLZD9ZXTKtF7QVE0Gaf0QzasfESQWUp3RWT1C/F9FAYERp6fwtJFuxw7iYOQjbW7O3Z4zssYqZ/X6QsZQrtAsBz4KA14FsLwNaucgUmWLGyg==",-7541336689490519797,-4849137374044637590,1201176250321384811,-7834899193630872486>()
                  ),
                  var2 + 14.0F,
                  com.yiyiaddon.l.b.d.c(var3 + 30.0F + 26.0F, 10.0F),
                  10.0F,
                  var23
               );
               return;
            default:
               throw null;
         }
      } else {
         int var11 = com.yiyiaddon.l.b.j.a(var6.uT, var5);
         float var12 = var2 + 14.0F;
         float var13 = var4 - 28.0F;
         float var14 = m(var2, var4);
         int var15 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2ekd8tlks5tky","c/ZLLAQprxebkH957QSaMLMljZKZubtS1Aip3GlgKRQ=",-5224627172787496076,-2768150729446138538,-46165289295938426,-8885638221776066304>()) {
            case -709576514:
               while (var15 < Math.min(this.dt.size(), 6)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3t0bo8z0sqnce","VMEyOaP9UwiMaUlmc/FaXJH9faxjuoSYCZJKsp1Cb5o=",5288891095632653569,-2705436105715077439,4671875023964977054,8231700587268990169>()) {
                     case 941705875:
                        com.yiyiaddon.h.d var16 = this.dt.get(var15);
                        float var17 = var3 + 30.0F + var15 * 30.0F;
                        boolean var18 = b(var7, var8, var2, var17, var4, 30.0F);
                        float var19 = var17 + 15.0F;
                        a(var1, var12, var17 + 3.0F, var13, 24.0F, var18, var5, var6);
                        float var20 = p.a(var1, var16.w(), var12 + 6.0F, var19, var5, var6);
                        float var21 = Math.max(24.0F, var14 - 8.0F - var20);
                        com.yiyiaddon.l.g.a.c(var1, com.yiyiaddon.l.b.d.a(var16.m(), var21, 12.0F), var20, com.yiyiaddon.l.b.d.c(var19, 12.0F), 12.0F, var11);
                        n var22 = this.bi.get(var16.s());
                        if (var22 != null) {
                           label54:
                           switch ((int)com.yiyiaddon.m.b.a<"s2fen2fizh4c9e","hOTP3po5Er+tWx+DPIOpuxC0d381/VWcSRYhZNMkJaA=",7608309412373474630,-2033338777527231707,-1479608111823453548,-2313161375436281711>()) {
                              case -192231780:
                                 var22.b(var1, var14, var19 - 12.0F, var5);
                                 switch ((int)com.yiyiaddon.m.b.a<"s30wx4mp62dpzx","woxoeyPFXIW2LYDJezWnyKhwqTYaCd/mQAAQ8hExxTQ=",-7208899851486910431,5329439527970017785,4938036790243485727,-575643755158867280>()) {
                                    case 848144093:
                                       break label54;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var18) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3sqahxhw75jme","XxFf3SiN918VIWTPFoZkrcJQXRAz7wo/FwvvqcETaQs=",-1150732439595653168,-1229392278781776195,-7573587884342992568,-69707670631668369>()) {
                              case 191810019:
                                 if (var16.c() != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ubp6wvzk5yn6","JHHwfL3UXx7il73/KIyHetAcDqd1yXH+ddHed8mAsfY=",-6541403791885649144,-8466303185472094426,5961926405553344649,4800222455847342893>()) {
                                       case 1267960481:
                                          if (!var16.c().isBlank()) {
                                             label50:
                                             switch ((int)com.yiyiaddon.m.b.a<"s7x5qunt59vly","RuG+Mq7+BCt5LT7eb1CK5keufHByIoP29xvZQJcYPho=",7772863214473333678,-2748812276745837797,-8912066429578156691,3723688815471899079>()) {
                                                case -763579609:
                                                   com.yiyiaddon.l.g.j.c(var16.m() + var16.c(), var7, var8);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3sr0371bpfgqz","iBFT03FuUyqfEQU5rs08eX7ZrV3QW3MCUyF3ZWK5zZo=",6047552705943510696,3166188312390538700,-8410375483778406907,7502577217277271594>()) {
                                                      case -261645928:
                                                         break label50;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var15++;
                        switch ((int)com.yiyiaddon.m.b.a<"s11xu0jbck1lvo","6qZM3MQARwBWRjzUSY806/4Zcff+jE4rkK48OC+yb/o=",7040760822592187402,-3811953684957452491,-7028221073028811634,-7875045190827994750>()) {
                           case 1386931940:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }
   }

   private void c(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6, float var7, float var8) {
      this.b(var1, var2, var3, var4, this.N(), var5, var6);
      List var9 = P();
      float var10 = com.yiyiaddon.l.b.d.c(var3 + 16.0F, 13.0F);
      com.yiyiaddon.l.g.a.c(
         var1,
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"sx0a1872nzl0x","62d9H+IMkwfRUMx0SQF2M2gc78p+korChUG67KQsLA9YC3W0",3751564546432473392,-1367251369183915624,-4216741117504713970,-8125497329703888318>(),
            (String)com.yiyiaddon.m.b.a<"s1wna4aw68702v","IpDg8oRwz1VWmBPWxC49u7ENNLOQEofQAMheGALR21TrrJ9TWh7mIO76Thz6JpLZWFljd72q+xiicw==",8751046308649676496,8656580870666293481,-1747184627397720886,8289517371311618002>()
         ),
         var2 + 14.0F,
         var10,
         13.0F,
         com.yiyiaddon.l.b.j.a(var6.uT, var5)
      );
      String var10000;
      if (var9.isEmpty()) {
         label62:
         switch ((int)com.yiyiaddon.m.b.a<"s1j8qz5od9g8s4","sy6GAd7No9LvD7zT749emysG+Ok+HgRuIiHBZ4qW4cU=",7636670140037343533,1976751828503766182,3774998749851225339,-1078062419986882817>()) {
            case -633054466:
               var10000 = com.yiyiaddon.l.a.w(
                  (String)com.yiyiaddon.m.b.a<"sts1wf5hfanrs","JuzJMmMnhBOh/5R1C7hhpy5EaWteZDozxSM2QblB",707827861662729603,-4756844853557567200,7476160284515973172,3489576941906705899>(),
                  (String)com.yiyiaddon.m.b.a<"s100t1h05vlz1l","j14G7rs6/TT0I1WzYUT73RpfnYx/v+OO2IdwACtuJl4D/P7J",-1002739967907929073,7295904659254668315,-9210471283951595582,8344280390235656586>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s30oechfwrp5i7","imwmVchMWAJtzNuvPN0sqlHcam5DZQbFpqpXYASjogk=",2889039972655397510,-7167178594475458216,-6381035478562870324,-5738018207206717397>()) {
                  case 1310826636:
                     break label62;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var9.size()
            + com.yiyiaddon.l.a.w(
               (String)com.yiyiaddon.m.b.a<"s3ishbaml03bw4","mbHa671bVEIX7fEAEj7KYftCtIyl2KXuIZb7KGep",1952506917863026926,3980473497673875507,1411272943410976481,-4608365844768532558>(),
               (String)com.yiyiaddon.m.b.a<"s3vq8v2s60d82r","QDQD86b+iX7yf1pjIXY3/oKPsDA1gC2B6FLF6+DtR70BEttdNwc=",-6684994778896857191,1402219455159700334,7869406007871866013,2957800944537470261>()
            );
         switch ((int)com.yiyiaddon.m.b.a<"s3qszxaz3t0rj3","VJ1PTp/cQC0+PjmMb44WcTesxhRlumu0gnliSPMgonc=",6604323636918094443,-111352146389332297,-2370615815008756300,267128826040561719>()) {
            case 733990236:
               break;
            default:
               throw null;
         }
      }

      String var11 = var10000;
      float var10002 = var2 + var4 - 14.0F - com.yiyiaddon.l.g.a.b(var11, 11.0F);
      int var10005;
      if (var9.isEmpty()) {
         label56:
         switch ((int)com.yiyiaddon.m.b.a<"s8ykyuyxpihmf","i98wgQIjudiZ4VXkS0++o5TqJAZSPiLP7JLwOwG359Q=",-9216304698107825531,4426881586411747357,-4733562620910273132,-5497108153797560521>()) {
            case -1404195663:
               var10005 = var6.uU;
               switch ((int)com.yiyiaddon.m.b.a<"s27e9ipbiup9pg","0F/k0T+uqy+NAkOUoliV/PtKMnEONm5/QcK/oErYl3c=",-8937559989660626625,1315650271749395486,3940117101878015044,-6437454520269979073>()) {
                  case 240020632:
                     break label56;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10005 = 16096779;
         switch ((int)com.yiyiaddon.m.b.a<"s1qaxsvbmo0ll9","JGwQudR/PtIFLHUDdoEL6QzOlPBaa9yqe3IVvKdTmwU=",-6843655382364195300,4573074908866691582,-2786450486867506639,9192078109615468159>()) {
            case 277286937:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.g.a.b(var1, var11, var10002, var10, 11.0F, com.yiyiaddon.l.b.j.a(var10005, var5));
      if (var9.isEmpty()) {
         label51:
         switch ((int)com.yiyiaddon.m.b.a<"s3qss1p1uoh6nb","SA+i+JwnZcRD3vFKoe7OKOGhizOgi3Yz1iCgYY2HuLQ=",-3308412263912263139,8820043962418485879,-8567603937340977429,-6662174286908080252>()) {
            case -870444768:
               com.yiyiaddon.l.g.a.b(
                  var1,
                  com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s18hmwje73doav","zgm1f1TBzb9d/DlxtMbWzVZv92vVaYbd37dXwfMg1kqOZbCIFjr06ew/l/hJ0eGa",5826548282067440010,7992479016623491087,-3360080868043358926,8080182976145115689>(),
                     (String)com.yiyiaddon.m.b.a<"s2abv9e40vf3pw","ARtCgRAWL38tUv92YadMnRFEiYx5f3KRxQaOqlaPb4+fSE2HiZyqYtXL6VXmF2Qy8yqD0Tlicds290ENxJrFNkuWXw7SkDDAVPBo++BLkySQEO6SEWM=",2792656730732036474,-3682327604805496543,-1616755907905376278,-8846487201630530891>()
                  ),
                  var2 + 14.0F,
                  com.yiyiaddon.l.b.d.c(var3 + 30.0F + 14.0F, 11.0F),
                  11.0F,
                  com.yiyiaddon.l.b.j.a(2278750, var5)
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1cjvyahamkk9s","dJkCMk/U4cQ5zlNWbeq4yovSBHvi/mD8oFUYO/iGEc0=",6766596055417225240,-8947114654310085067,-8215660879028465287,-105459343236949137>()) {
                  case -800872161:
                     break label51;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var12 = var2 + 14.0F;
         float var13 = var4 - 28.0F;
         int var14 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1t9ifyupm0hdy","od6uoVQOJ+ghG0AQwPZqca/UnWnb7jRLGaVnhKjZWJs=",-6256270743771220834,-6521722865099459315,-3673251737462813496,4689424239029313971>()) {
            case -1377866805:
               while (var14 < Math.min(var9.size(), 5)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s12t01m7tnk4pn","qB5PEpv4xC95mdjq67aWBGZxMJZAlVu+YF8Z5qZmhJM=",5284352938923806541,-8914962429701400612,-8059298065111714050,-394368743414439751>()) {
                     case -691751332:
                        e.a var15 = (e.a)var9.get(var14);
                        float var16 = var3 + 30.0F + var14 * 28.0F;
                        a(var1, var15.b(), var15.B(), 16096779, var15.b().m() + var15.B(), var12, var16, var13, var5, var6, var7, var8);
                        var14++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1syjq3vgc55nv","tYau7Rq9NbpOm3XFRC3gMwrfV3E4GEO3WmluV4rBEEM=",2371915922014570224,3265705390743712416,-3870089239563243258,-5210084993218761919>()) {
                           case -101917158:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      float var17 = var3 + this.N() - 8.0F - 26.0F;
      boolean var18 = b(var7, var8, var2, var17, var4, 26.0F);
      int var20 = var6.uS;
      int var10001 = var6.uT;
      if (var18) {
         label41:
         switch ((int)com.yiyiaddon.m.b.a<"sgbcydu94fka6","7NYwRPG0XTEQSCoXHS0HyVuPXSnitKRDxp5wF65isow=",-5428013809160683713,-4200232614255359248,-5367163729853928424,-3986510185829172816>()) {
            case 1780072833:
               var10002 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s2uqkjxrr48sea","Ut2evqw5Uz6/3T0ogDqVO8HMo1b5P4iGPDp27f9gT+E=",-3931504361944581533,7346568189726829917,-747775037528416454,-7002777330958011357>()) {
                  case 165964928:
                     break label41;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s1nvbgh96me3e6","G3SMgt1rq9STydIdkZMAQxOIGbQ4lYOdAa7gVbl2WtU=",-238004131696205483,-6641931040974768120,-7946501752358961465,636379851305824212>()) {
            case -1167169774:
               break;
            default:
               throw null;
         }
      }

      int var19 = com.yiyiaddon.l.b.j.a(com.yiyiaddon.l.b.j.a(var20, var10001, var10002), var5);
      com.yiyiaddon.l.g.a.c(
         var1,
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s3frtvrd2v116","dXqe/GZyNMqYYFeMKXHjJ0NEaGln2c6Yodpyp2x/fzm+OBIN3y3ciw==",-6755540281065898398,-7913839668984005484,8591711177011457190,6996692969206983809>(),
            (String)com.yiyiaddon.m.b.a<"s30pcgdwa9apvf","CN499JOpD0AuzUSim7woY6rpX1YvS9/LXZ+BJgAAKVhZYxozG5s6gY1AX29tBUi9L3nq+2FWmvtpmIiA2G+fmg==",-5515136548313622958,3021558158569528637,-9024630726067978,3140398504989499485>()
         ),
         var2 + 14.0F,
         com.yiyiaddon.l.b.d.c(var17 + 13.0F, 11.0F),
         11.0F,
         var19
      );
   }

   private void d(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6, float var7, float var8) {
      this.b(var1, var2, var3, var4, this.P(), var5, var6);
      List var9 = this.bK();
      float var10 = com.yiyiaddon.l.b.d.c(var3 + 16.0F, 13.0F);
      com.yiyiaddon.l.g.a.c(
         var1,
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s150wrmxq2vw3l","dpwzhCSUAtcKhJm2prLDAPGrSzJkon7eAKcQsdr2+ZfX2g==",-3698186787622234494,-1071875312349427206,5509076179608845976,-3996555532953513507>(),
            (String)com.yiyiaddon.m.b.a<"s1vly7mdod9pyk","Am2RvecMYlpbpQ2esCNKnBQLg/nC4XPsdu3HwKv9XIAm0baIi2yE3m9d",5940320817798827949,-5688033632451005265,-6061041466577371910,6110191779393246169>()
         ),
         var2 + 14.0F,
         var10,
         13.0F,
         com.yiyiaddon.l.b.j.a(var6.uT, var5)
      );
      String var11 = "" + var9.size() + this.ds.size();
      com.yiyiaddon.l.g.a.b(var1, var11, var2 + var4 - 14.0F - com.yiyiaddon.l.g.a.b(var11, 11.0F), var10, 11.0F, com.yiyiaddon.l.b.j.a(var6.uU, var5));
      if (var9.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3j7h8csxdrcb","P2YZWFcxxhkPEddmA6G6bvVzvBJwBiJRQdzYJiryykY=",1622706612115883337,-5362247342609825334,1466649903569066794,-1824275942976791511>()) {
            case 391770775:
               com.yiyiaddon.l.g.a.b(
                  var1,
                  com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s1u2fn7seocdxl","tM2XWeylH+yPKPKQRh0Tb+PaJ9SzIJqsVao/+qvE4mhGmqw/KnuBhcfzjug=",2841978810475227122,8258101489733167735,-2028681346776213050,-8995451604053615304>(),
                     (String)com.yiyiaddon.m.b.a<"s1vna0oxypm8xw","tDUeiFUdj8zeIhit8iCn1xuBUDuaNMg+fNuwKPLbp2cXoKQ11mWEpT02ZiMGonrC/2TvU7o7LWpri9Fr2OI2D8BrlaY=",8764360938334941340,3031642257596173809,-2757819362157993132,316166253880386259>()
                  ),
                  var2 + 14.0F,
                  com.yiyiaddon.l.b.d.c(var3 + 30.0F + 14.0F, 11.0F),
                  11.0F,
                  com.yiyiaddon.l.b.j.a(var6.va, var5)
               );
               return;
            default:
               throw null;
         }
      } else {
         float var12 = var2 + 14.0F;
         float var13 = var4 - 28.0F;
         int var14 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s32z8rvrusq3wu","tPyD4iuzeFFjXaq6L/UZfY7J8P0uWmDB4tc3tV3UAoc=",-6074766839033127106,-746626415225467426,6591029311548430800,-7555959705825540658>()) {
            case -465576303:
               while (var14 < Math.min(var9.size(), 8)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1cip97koo3ifn","bv9IEXJdCAscijZw+QDFYMwNYDdVVJPWVcjOe/ixjO8=",-7837454721384050558,-5257318886845347653,-2168788615217888489,1621181068625587723>()) {
                     case 1945903569:
                        com.yiyiaddon.h.d var15;
                        float var16;
                        e.b var17;
                        boolean var18;
                        String var10000;
                        label68: {
                           var15 = (com.yiyiaddon.h.d)var9.get(var14);
                           var16 = var3 + 30.0F + var14 * 28.0F;
                           var17 = a(var15);
                           var18 = aT(var17.gM());
                           if (var17.B() != null) {
                              label48:
                              switch ((int)com.yiyiaddon.m.b.a<"s3yndnxit0j5w","lqe9zJfYUcmO78IR7H3eYvVTX7TyMyp7+/KqcWcWVQU=",-4851780246095582998,-7354078714662702755,-679750011856328531,7624445636073438586>()) {
                                 case 2063275465:
                                    if (!var17.B().isBlank()) {
                                       var10000 = var15.m() + var17.B();
                                       switch ((int)com.yiyiaddon.m.b.a<"s10mx9a2lafylz","V1C5PvSsFtglsS9kARa6NxxUnQJtdBEi1N3iy88UtfE=",-6225899022172311417,8906404262588515812,3650759604209034649,1295293946222954649>()) {
                                          case 167343093:
                                             break label68;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3q4q8dfibiug4","O2YvicGONVXpJZHtRbfvJB9LDLTcwjNi8B7pmCKwdIs=",-8508749150229799473,-5612189609623875594,2528131672999819004,7242961031970521829>()) {
                                       case -465094552:
                                          break label48;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var10000 = var15.m();
                           switch ((int)com.yiyiaddon.m.b.a<"s122tq3xlywbx2","/TQcsQCmXKdmQfQf53WysjYH16b5T7+ZZ3xCx2x52+4=",-4564842348905490381,-712275177231231363,-7947628034158322624,873786192758974215>()) {
                              case 11555944:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        String var19 = var10000;
                        String var10002 = var17.gM();
                        int var10003;
                        if (var18) {
                           label44:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ycpwcklzo0ew","jdZtDDUDsGH0U59Qybve0idLlq6p+RtfjLXg19n89d4=",4130687408341550661,7340643238246999494,-3304869161807948500,599530530854425117>()) {
                              case 1892409898:
                                 var10003 = var6.vx;
                                 switch ((int)com.yiyiaddon.m.b.a<"sqcmbupomh55u","kMzejKQW1XvNvVElWpJMJ1JcXClkEm4VwTSTrFTei2k=",-8442002894603207952,-2323736783997312377,-9106996423486655349,8897477675341863837>()) {
                                    case -68479073:
                                       break label44;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10003 = var6.va;
                           switch ((int)com.yiyiaddon.m.b.a<"s31zfiyjdivdpl","HioJIppKjgXeMG1LYn2e79YWEI7JI6WjLDnPVellG1k=",5290690127669851170,-7416810286143282385,-2422699324533218119,-3819087776295700789>()) {
                              case -201369167:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        a(var1, var15, var10002, var10003, var19, var12, var16, var13, var5, var6, var7, var8);
                        var14++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3jciycekzdpzs","meVNIZt7OaMBvSaT4TIsgzOVJbfPg2GxbJIaiszV9lA=",1721436222730126405,-8121124939667764343,6881063972035617447,-6929074197994173235>()) {
                           case -920837876:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }
   }

   private void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, com.yiyiaddon.l.i.c var7) {
      this.b(var1, var2, var3, var4, var5, var6, var7);
      com.yiyiaddon.l.g.a.c(
         var1,
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s35dja43xfvhkb","wM/p8MqBF4S1GgA9MUuzBQ2nuGBE01fVH7ILTyCFZAmk1Ven1xKHPXbpAiZEyA==",-3898388588638131973,-7082786798324934492,-2298861236153275269,-2818130492749019024>(),
            (String)com.yiyiaddon.m.b.a<"s26byd8xfc49cz","GlFBReFTF3bharHb94pma4hrhmRtVxJJuTFB9FjeQoxbpYmJ70yvEgocfqAgUx7glHVb51ph54hMnhmrPvZG2YZ/pE67Yq7yTQRe/A==",722630246790731148,6305378509501314364,6944136507388362405,529595986882214227>()
         ),
         var2 + 14.0F,
         com.yiyiaddon.l.b.d.c(var3 + 16.0F, 13.0F),
         13.0F,
         com.yiyiaddon.l.b.j.a(var7.uT, var6)
      );
      int var8 = com.yiyiaddon.l.b.j.a(var7.uU, var6);
      int var9 = com.yiyiaddon.l.b.j.a(var7.uT, var6);
      String[][] var10 = a();
      int var11 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s198ordsuadvl1","fl6xUHoAKWHQKb18zsfoyMdDpcNk2fdxorLKMXXSopg=",-595739630417974368,5583883242235404908,-157704404623972059,8336787632908942225>()) {
         case -318204564:
            while (var11 < var10.length) {
               switch ((int)com.yiyiaddon.m.b.a<"s36pcc1fmcr2e0","9DiWYQ181gZjV61dRyYeGKAO9FtD41GFeERFWZMti4Y=",-5948443512916998031,6593193702626592170,-5250442169148433806,4429130847701119273>()) {
                  case -2015149932:
                     float var12 = var3 + 30.0F + var11 * 28.0F;
                     float var13 = var12 + 14.0F;
                     com.yiyiaddon.l.g.a.b(var1, var10[var11][0], var2 + 14.0F, com.yiyiaddon.l.b.d.c(var13, 11.0F), 11.0F, var8);
                     String var14 = com.yiyiaddon.l.b.d.a(var10[var11][1], var4 * 0.62F, 11.0F);
                     com.yiyiaddon.l.g.a.c(
                        var1, var14, var2 + var4 - 14.0F - com.yiyiaddon.l.g.a.b(var14, 11.0F), com.yiyiaddon.l.b.d.c(var13, 11.0F), 11.0F, var9
                     );
                     var11++;
                     switch ((int)com.yiyiaddon.m.b.a<"skwtqi6gpcczq","3QCLYsHMCrJS44hFgU7ERKVdrxaAHTSizXKN6NrPSzs=",-5094021496869666522,-4548122315822118482,-6811740748864680177,5278828000053620263>()) {
                        case 614373433:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   private void e(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6) {
      this.b(var1, var2, var3, var4, 104.0F, var5, var6);
      com.yiyiaddon.l.g.a.c(
         var1,
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s1rsrn6xmw12j8","OIQDAlCWw/FYxnKX8Q6Jq9T9g6sneeAbH4IgMC4bVIZmrioI",4581772970844967066,-8136654765690898008,-1353548998069877712,5323054761020803431>(),
            (String)com.yiyiaddon.m.b.a<"s21h95pe8ssws5","K3WdF7Jo8ZnPVqfnk7kNm9K8u/CFZLM6qqWOPO3GxnOsQYJ0Sc6PHinHeH6Z0whZPGFZ857JrfS40w==",-8422854182788060594,-1381839193538325039,7491345450551447636,5306582387984971253>()
         ),
         var2 + 14.0F,
         com.yiyiaddon.l.b.d.c(var3 + 16.0F, 13.0F),
         13.0F,
         com.yiyiaddon.l.b.j.a(var6.uT, var5)
      );
      float var7 = var3 + 38.0F;
      List var8 = com.yiyiaddon.k.a.d(3);
      if (var8.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s29qt3s1cznark","f1Z8bkGQWmWJ6GjaEH1o80ztYvMIjDW01hqSjp01j6c=",-960197447804469218,5809086244676479892,-4151015245617790610,4308903439612496053>()) {
            case 350226519:
               com.yiyiaddon.l.g.a.b(
                  var1,
                  com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"smsp23cj8ynkg","rBxgv2Xtf51n+EdDVPy8S52mh1oDexhwxGQMHKZ58vblfegfazj5Dw==",-1010003005569210708,8173406436749169804,1146802846017670201,3970416427876036563>(),
                     (String)com.yiyiaddon.m.b.a<"skjmbfn54pqd9","U3kdiH/sOd3jwLq0cLs8ZibQ01dfzPQJkrUmItyFSIIJ8DNd6B0cbuG0g0f1Un0vKcncbM1p7vpBlg2fsBkskA==",-202383656427571804,-1502527009254221893,4349138143610239334,-1876608231520798131>()
                  ),
                  var2 + 14.0F,
                  var7,
                  11.0F,
                  com.yiyiaddon.l.b.j.a(var6.va, var5)
               );
               return;
            default:
               throw null;
         }
      } else {
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1mdmdoctddteu","fjktaNwfejXM1lFpS/QUzvQpB51eiECU612oJh7Agy8=",-8731284735054023645,5241225844550013762,-140042372220997323,6999544010067682302>()) {
            case -2146413254:
               while (var9 < var8.size()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2239ggdgiffmw","GiWvYplTVUealWi43jlSB5crnKiVWf9Og3F7p8DIYgA=",-6840642739221226277,4037356793083132991,-9059316428086005739,-6462878111526295964>()) {
                     case -1493312515:
                        com.yiyiaddon.l.g.a.b(
                           var1,
                           com.yiyiaddon.l.b.d.a((String)var8.get(var9), var4 - 28.0F, 11.0F),
                           var2 + 14.0F,
                           var7 + var9 * 22.0F,
                           11.0F,
                           com.yiyiaddon.l.b.j.a(var6.uU, var5)
                        );
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1g8d4o5qwho0c","jofNDLcYsuM4SaruRTxiqlnP9GfcWy2Edy6I1wgqrgw=",-266159121015314863,4836168073486203273,-7237456686726357743,-3368430015126674182>()) {
                           case -1754715503:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }
   }

   private void a(Canvas var1, float var2, float var3, float var4, int var5, int var6, String[] var7, String[] var8, int[] var9, int var10) {
      float var11 = var4 / var5;
      int var12 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1ytq29c4e28qf","1IYUvGfh6oBYSsW7qZdILXRqm1UJ/UyvX3CdGm75kf4=",5949403964615978652,-1441716084573191693,-4564451433443090683,2523628256308181012>()) {
         case 1466344564:
            while (var12 < var5) {
               switch ((int)com.yiyiaddon.m.b.a<"s1jnk0zt8fw0kj","Wv+CItXfxQHvHpGhvV5pC9Ps+1vbAv1RmMiu2V6eCpQ=",1817249945483273959,-940329057185548932,4680076757390012366,8642117068651252866>()) {
                  case 1675296939:
                     int var13 = var6 + var12;
                     float var14 = var2 + var12 * var11;
                     com.yiyiaddon.l.g.a.b(var1, var7[var13], var14, var3 + 15.0F, 10.0F, var10);
                     com.yiyiaddon.l.g.a.c(var1, com.yiyiaddon.l.b.d.a(var8[var13], var11 - 14.0F, 12.0F), var14, var3 + 35.0F, 12.0F, var9[var13]);
                     var12++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2txkt9ps6jw72","f0iEvpAVheuDvWiTlDS3n5xvaBdZir7guRoCXnpSjpo=",-859974164321451833,2789278451042689602,8595708031737418011,6405346302676235747>()) {
                        case -1775409654:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   private void b(Canvas var1, float var2, float var3, float var4, float var5, float var6, com.yiyiaddon.l.i.c var7) {
      m.setColor(com.yiyiaddon.l.b.j.a(var7.uQ, var6 * 0.4F));
      var1.drawRRect(RRect.makeXYWH(var2, var3, var4, var5, 10.0F), m);
   }

   private static boolean b(float var0, float var1, float var2, float var3, float var4, float var5) {
      if (var0 >= var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gdog2u8txnkk","OegjjYbIA2KvTGNEK+z2RoGFJOGbcuk3akKu4jVFbn8=",2206907242397241186,5697040512902784065,-1606979049275321942,-3587437144747400188>()) {
            case 1733782976:
               if (var0 <= var2 + var4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1u48u8qinxyn2","JlDpAcKgMpo/Ehok27vCgaXxlwWzD2H+z9tYAkdDLk8=",-4949765916845461628,1743698215377734992,-2828637581692649480,8085242479289638892>()) {
                     case -2140107458:
                        if (var1 >= var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s18hzn0j85x9ka","+C9/qnKRAS6Oifo1g1B1K8/HWg7sN1wYJw8qWe77ulo=",988712659719855443,6339430851425349778,-158140265009446599,341991184043189810>()) {
                              case -1175255853:
                                 if (var1 <= var3 + var5) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s30tzz4e0qtvi0","wTeXH4wpt8/WlKPVlgCQaGBpV5t3MM4IkoOaZOyRWPM=",6536679491217092571,-6456735284975766248,-7501449635380852834,-1820386739463704077>()) {
                                       case -225893430:
                                          switch ((int)com.yiyiaddon.m.b.a<"s32lr5a6zjw9lg","30VLR7K9eRmu5L3UTf9KiuBFLOLkdWv09psB+BAbfB4=",725668200365825691,1969029867759674508,1303246176035722084,89556588324714141>()) {
                                             case 1456299238:
                                                return true;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s1zgo5k1yv9kds","zJ7/d473aFG7Gld7JsQB4Qx7OSFF94xW6RD6/ywpExc=",-1759434909453886627,6366157612562990639,5061960757261892714,-1775708638348630790>()) {
         case 1949133911:
            return false;
         default:
            throw null;
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      if (var7 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1x7t5sy7su8wq","tcXjQ9DhjXqaHuYgz66gUcw16eFt4Zf2/NHwyveL+UY=",-2120952599336570898,3447920681629185433,-5905568141130745801,226712409256349190>()) {
            case -1686277450:
               return false;
            default:
               throw null;
         }
      } else {
         this.kb();
         float var8 = o(var5);
         float var9 = this.j(var4, var6);
         if (!this.dt.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2k5miytoqah4t","XnndcS00/fT9kbWsKZIlTs6Xka8b7UGgzci1sg5SWrU=",-2412827332655766074,1589486679256654000,-1808823695030997439,-3606974721374983949>()) {
               case -194329604:
                  if (var2 >= var9) {
                     switch ((int)com.yiyiaddon.m.b.a<"saiy5ozfp9hwk","1uPuD+s6gHS+OXaa4VO6TYAu2U4Fk91f841ceFDS6as=",661340129191132550,-6213045007171982129,-4187757412653146037,5756703336482932424>()) {
                        case -687406736:
                           if (var2 <= var9 + this.M()) {
                              label148:
                              switch ((int)com.yiyiaddon.m.b.a<"s1d1okbmui7fy1","ZemvAx50wKvFuqa8ErvACg7KxLUE1imOSXjuYEr58lA=",-6279618345643652840,3823563248249986053,-3376258696393125950,6276026165024271559>()) {
                                 case 1971969490:
                                    float var10 = m(var3, var8);
                                    int var11 = 0;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3z24imujltfk","v212nznNYmNpRLCBWdiJVdE9Kuj99Ud/Q7u6bzjQnJc=",-5510506635561512122,-4444083972584333085,794523987262516439,3375268345582482493>()) {
                                       case 1487753942:
                                          while (var11 < Math.min(this.dt.size(), 6)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sa9h9gv6bufa4","UwP40o1TyO5okDhTFSBMUJ7Uqaq7HxXXJ87nx34cCjQ=",4312898133484367878,8161390797946297752,-714527699746332343,7314445259112711493>()) {
                                                case 1196081018:
                                                   float var12 = var9 + 30.0F + var11 * 30.0F;
                                                   float var13 = var12 + 3.0F;
                                                   if (!(var1 < var10)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sk276kbmud48f","3eBkfhSygdYvg7S7gwWKnySgGdCXU4fEwO/MQm9wpsE=",-4481607529820788536,-9182873856326957491,4056654240868564117,-457600827689999845>()) {
                                                         case 322150718:
                                                            if (!(var1 > var10 + 44.0F)) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2y1gynhqh1tje","aE3kMiaMJ6ZIZQPF1UDk0w/u10yU4Ffp47G/OimjIUg=",-9065608725083322345,8269553177230118001,8341133710309859446,-8817536167126602934>()) {
                                                                  case -1048021895:
                                                                     if (!(var2 < var13)) {
                                                                        label156:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s3kutw3hzgbaga","58hoFerpWc2Yvx0YtujBwrPsuMrNZl+wc/4qqhyW+Tg=",-2048764352136535626,6976754798358654269,5021835586576035484,162300322252036514>()) {
                                                                           case 1482859985:
                                                                              if (!(var2 > var13 + 24.0F)) {
                                                                                 n var14 = this.bi.get(this.dt.get(var11).s());
                                                                                 if (var14 == null) {
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s3lt5fun55a0at","LD+d3XzFA0yvHwak0Ncdy/9u/AyycrfEiei88T/5oFg=",-1821505631664279920,7465313375770949211,-8211293192255870992,8193942792232306879>()) {
                                                                                       case -324027714:
                                                                                          return false;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }

                                                                                 var14.lg();
                                                                                 return true;
                                                                              }

                                                                              switch ((int)com.yiyiaddon.m.b.a<"s15kwdge1yb7e2","mHOfk11kiBqeFrH72gKO468BFiiZnTCjf6bJ6kH+BYY=",9045476255762300836,4855258556821518912,6222657686462214857,108493480971343123>()) {
                                                                                 case 1382800620:
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s3m2ynn6lsq6ul","0kz4nwsW9xyZ17Ca/j/mvpSU+PRgARh7ipkZ/QNd56k=",2074624417334500006,2711828544962654161,5086596415466488052,2259302157818016029>()) {
                                                                                       case 1588750282:
                                                                                          break label156;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }
                                                                     break;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }
                                                            break;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   var11++;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1q0hy3v1hot3y","Uw9GJE/9S6R7SS1Gd8qvTD90TFaq9xIV2Lyz0pEz3IQ=",-5972341649302862015,-3512031101059454843,4157127410916762515,-4575832562524443254>()) {
                                                      case -2147130867:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break label148;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         float var16 = var3 + var8 + 12.0F;
         float var17 = this.j(var4, var6);
         if (var1 >= var16) {
            switch ((int)com.yiyiaddon.m.b.a<"s2leqlp8koll8p","fqlEroUMfUDd8pHECS1MqhTH+yazlgCdsNWL4vob3V8=",5466894652384435975,-4176604463488264146,-3946845906838908650,-8301066878873919425>()) {
               case -198270335:
                  if (var2 >= var17) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2kjhzzrkwiohv","OKw+ZGY7dPZD6f8DGlbh6NPsUpqxmfatlVWLf45sKo8=",-2863235102040418777,7179946415526150663,-8755627022891561098,-251521425114124623>()) {
                        case -1394163307:
                           if (var2 <= var17 + this.N()) {
                              label130:
                              switch ((int)com.yiyiaddon.m.b.a<"s15m1f9ooxh8fs","3Zyimtd4aH8nW7cjSaWjfV25ObQchSxjj/Tk0K6mAR4=",-8632475880490832489,3797252598423601018,1725068937405021749,-2972904373053192694>()) {
                                 case 1343378952:
                                    List var18 = P();
                                    int var20 = 0;
                                    switch ((int)com.yiyiaddon.m.b.a<"survmj7392swy","+drt+2DAM67jUvJJOwOj/RRwhYG8k/xe9dv+fq81FDo=",-6317727845787971696,7819024922377813525,701750401944380095,-5363798388261768356>()) {
                                       case -78680745:
                                          while (var20 < Math.min(var18.size(), 5)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3uywidry8rhlv","dIRaI1rMORGCLDCFX+CxuQEwE7t5r4CG5p4ofv0cdRM=",1597732508227274846,-6340182128514672798,-8041369605104793849,-5981557090267479959>()) {
                                                case -1501373529:
                                                   float var22 = var17 + 30.0F + var20 * 28.0F;
                                                   if (!(var2 < var22)) {
                                                      label138:
                                                      switch ((int)com.yiyiaddon.m.b.a<"sva8g3lcehkr4","HWXXvYs+b84Lggr29HGsxc6WiQK2ypu1f15VYmz0mp8=",7682549866866501754,2237452872892086304,5813835259398134310,-742273621904645505>()) {
                                                         case -1261959276:
                                                            if (!(var2 > var22 + 28.0F)) {
                                                               this.n.accept(((e.a)var18.get(var20)).b());
                                                               return true;
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"s1ps5peoxngv5h","qLmzIsQYaZCuUUO/zmoehvLy1fnzC4aVR+Qxq+g1UYQ=",1150168889682520986,5070365617174531694,-9013137467867821763,5787535697436915390>()) {
                                                               case 749047667:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"syv4p7ahywryy","nWhL3JoVGBV+3Lh7WDdpq7YrQH3rg2CthWOibF7VowU=",-260520716873261049,7156437242065475820,-2347917549528198099,-8617037774905954250>()) {
                                                                     case -2100256075:
                                                                        break label138;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   var20++;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2nz3px3xj4mow","D80SGDafLKMCwg8ncrsGZlF3i9c4RdK805q4oLsns04=",-5035417981514919552,6460418919845847372,-4598524906723217144,8607268141054887348>()) {
                                                      case 339513019:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (var2 >= var17 + this.N() - 8.0F - 26.0F) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s23yl2il9evi8c","x6fhBS9V5pDACaNJanLn8v+dvU18Z/91Hr6N20LBI48=",3603709146348904113,-6080138545456080834,-1157425102255409509,2426924631726657499>()) {
                                                case -374883325:
                                                   this.ka();
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break label130;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         float var19 = this.k(var4, var6);
         if (var1 >= var3) {
            switch ((int)com.yiyiaddon.m.b.a<"s3rp1iookniicz","XYnrAmkb0FuG94RlCJtm4bf8oqL7YEFTHiwkXDhNlN8=",600476568978311635,-211442722205591670,-6655400072805369866,4411505509346802420>()) {
               case 1759552291:
                  if (var1 <= var3 + var8) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1iwxlumlotomd","v/Cr6oOqsQTxtD2toZFVX609X1W0zcl/xZfTbXyBhO8=",-4023153831818726933,4824279684075713142,-9210986276330407539,-8028049746721976381>()) {
                        case 1052467102:
                           if (var2 >= var19) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1hjy6ugocy87g","19Y85tsxKih4zMQmOLny6JkwzClwL5xPbRZcezkk7N4=",-9090399110476820991,6919976312848830072,-6651789071181655604,-167031433224393135>()) {
                                 case -407949144:
                                    if (var2 <= var19 + this.P()) {
                                       label120:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3pg750f9fok8o","7VaQn6uuGOOCPoL7+NLTmPWsCvkwKr9+dw07rSPD3BU=",3298333989530452069,8694143496242045198,1622656321021922568,4445610750284384380>()) {
                                          case 1103588692:
                                             List var21 = this.bK();
                                             int var23 = 0;
                                             switch ((int)com.yiyiaddon.m.b.a<"s1dyhwnzy98831","jOY0t4gsjqVoONV2YjYQ/q9SChS+ZsgoVkeorTn2G9Q=",9219869875505659455,-7094118283656700529,-307146306525318544,-4800743373961584393>()) {
                                                case -218742613:
                                                   while (var23 < Math.min(var21.size(), 8)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3t14qvdm8yrph","TDtGIUP0NGmrSWw/Fw8S/Pcs6KG13V4zpeXutXqjdj4=",-1814995739734175051,-6976113024970602262,4717020063679391152,-3752583410662035025>()) {
                                                         case 2086694330:
                                                            float var15 = var19 + 30.0F + var23 * 28.0F;
                                                            if (!(var2 < var15)) {
                                                               label114:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3luoqrqmzwsbn","uVHARIbkfCH3Z/weHT7nLuVtiLdAdraMxXHl72/wkTI=",6962054644444374638,1415881924846512186,-6571090815383347280,1730461138034600016>()) {
                                                                  case -950657128:
                                                                     if (!(var2 > var15 + 28.0F)) {
                                                                        this.n.accept((com.yiyiaddon.h.d)var21.get(var23));
                                                                        return true;
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2kwsv4xx6dfpb","eSb0k40qggsQm+zBpG7/yX1q6zUMMv9xKJeBfj8SeB0=",1070266296709626718,4687964058828681759,3173223292368939480,-5793274377024506522>()) {
                                                                        case -1805142131:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s18sn2lmnigaz4","6HPt4xOrRxH1FHbaGhpdc2ZPmDRDzqzXyumy23ki7x0=",-5076731926458542501,7369567313487074462,-6600795169539104325,-4102596827658170197>()) {
                                                                              case 1159103235:
                                                                                 break label114;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            var23++;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3g1cq28u17gi8","qCdWxTqrL/GxEjgd0udJCFS9qzEk/+oxJwlNZD+bgaY=",7387329478697000745,-377219970947686086,-7142627715095450522,3200421525871483940>()) {
                                                               case 1561766498:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break label120;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }
                                    break;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         return false;
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, float var6) {
      return false;
   }

   private void ka() {
      List var1 = this.a.bH();
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s27s7flg2rtbkw","D4FyBqgKDwk7XyUwXfJh8fOCfP+MBf48+xjGRwDT6rY=",376692502772577005,-3314563942593219093,5788972813144531920,-2266489003954567469>()) {
         case 811771133:
            while (var2 < var1.size()) {
               switch ((int)com.yiyiaddon.m.b.a<"s32itlulqirtkf","+wE8GLrsS2qdKZmOTia6sQIyj4rkBblUhXP3xpkcJJ4=",-6056557129500236808,7762701583584762685,-2217984999168062341,3066209806434898122>()) {
                  case -585319901:
                     if (var1.get(var2) instanceof g) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3pbr292v4h8i1","YdQb1b3CVc+TEc1lgSokkKS0Ru6Q6kghnMTFc2QCKww=",-2459493389578813209,1712473272058530745,6074823842708719223,-6169202886022883444>()) {
                           case 458203410:
                              this.a.N(var2);
                              return;
                           default:
                              throw null;
                        }
                     }

                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2jns9dknuthun","weWolnOLfxI/E6zPeNm83S+Zna2Oi/QSiyeIyZGYrqs=",-619594755893739710,-2836703380505518726,-7037513564529797369,-8657287622668459710>()) {
                        case -1692245044:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   private void kb() {
      this.dt.clear();
      String var1 = com.yiyiaddon.c.a.r;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s9ienpm3iov5n","uc4bWgzWaAqTMVSi0LpleF19r6i+g/GE1dvSG+fyPB0=",-4613242434191343704,5052932966458338640,8366294027137853611,-7384755970210871556>()) {
            case 843287809:
               if (!var1.isBlank()) {
                  String[] var2 = var1.split(
                     (String)com.yiyiaddon.m.b.a<"s23i9k9jgpmivd","UEPAG4gq39wx+gsdfhm80Z1WrLIOqdFbmiyTcLkd",-8451866615968212443,-718322363133856210,3437115128940216655,-3101917791936105724>()
                  );
                  int var3 = var2.length;
                  int var4 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s1geddzol2s6wc","1iUxRtrsdxSLWkM+W+//zLzKbbXyb6b2YDHDsabQCqs=",8237367139410961412,1371055168117539950,-6989608283143083592,8966751600928157629>()) {
                     case -1691080087:
                        while (var4 < var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3uo2u1bnw5ig4","hvDHinebHhooaKzA/2IJN3Zmpz3h7cP06U9QxbmnWEs=",-405842223374838548,119310214130423385,3002387427078744113,7006596344875914687>()) {
                              case -1344045244:
                                 String var5 = var2[var4];
                                 String var6 = var5.trim();
                                 if (var6.isEmpty()) {
                                    label39:
                                    switch ((int)com.yiyiaddon.m.b.a<"s21h9issys90pq","4mIzddgR1IJVK93sAxHQoAS4ADmMEsJEa64zrxK0S+Y=",4493505505509608448,5016235717336713590,-2883344788328330728,5076885058482033778>()) {
                                       case 31328232:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2c7zyfr0nreog","UaPYIRxHjH+JWtG0dYy+7ExTULUuNWYdXHR5XR6fb7U=",-8860416454158649043,-4471968956189438523,4218583416304393780,4501525549706901366>()) {
                                             case 110429443:
                                                break label39;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    com.yiyiaddon.h.d var7 = com.yiyiaddon.h.e.a(var6);
                                    if (var7 != null) {
                                       label35:
                                       switch ((int)com.yiyiaddon.m.b.a<"sh709885686du","/4gTjj8Pf1ePELZfXotR8xdJp21CI/kdyZEzKRPZ1rY=",4307474282671999772,-7461432326577849167,3306109055581230747,8907917394103212071>()) {
                                          case 1434254250:
                                             this.dt.add(var7);
                                             switch ((int)com.yiyiaddon.m.b.a<"sfcpg7g2fzz4k","2r1HH3Z2cADE3ob1RDrqLApuDJuofFd/CsrT7/vYBDg=",1017100044288194235,6566387291282858874,-5665955822812034278,-2488965443362138488>()) {
                                                case -857381282:
                                                   break label35;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }
                                 }

                                 var4++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s30aa521unz6r","XuK/5WSG9Xpj9uozECcf6nqSxUKiFg46QoMJkWCJrXQ=",-5919608546987189558,7138185774069147433,-4870641742762925162,8108729117597792207>()) {
                                    case -1161179006:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1cv56ec7bnkxk","Iw3DNnJJ/5aVoOR0U+if4Ag5LW9lUs2XU7UXt3fi/ts=",-6115054449656924965,6097372745497756869,165626891636416449,5014267656876115521>()) {
                     case -2100189761:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private List<com.yiyiaddon.h.d> bK() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.ds.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2uqtkklon9jeu","q7ZNmIpxhO6p6xJAbJhgpcHTb6doR8UlzfSUp98rO30=",1980110077688671337,4760179572096788167,-8692129615846984216,-4179956630107982076>()) {
         case 1298717835:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1xoy9fwv71spx","vwnALiiSjYKogmbNPgnl7c/FQda0gmChU9N9OPX4T1Q=",6334787879657573157,-8473625912869465013,7213930014778793031,-9069126186839090358>()) {
                  case -1733737989:
                     com.yiyiaddon.h.d var3 = (com.yiyiaddon.h.d)var2.next();
                     if (var3.ar()) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s2biorphkz6h4","UD5uKzy1gHKQmE1udi7hM1ZeTTh0bElPWeP0dJ+blp0=",4090880071704739079,8985741471126697969,7439410158411833882,-7666620442226709835>()) {
                           case 216982754:
                              var1.add(var3);
                              switch ((int)com.yiyiaddon.m.b.a<"szdbzriy5py8g","fIbauUbi9fEGAPzWeJeUaZEsDWwO162Y6VYXeBviivo=",7332240022608407462,8654790444291051859,-2943646620841374505,2919696220123291418>()) {
                                 case -1027896669:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s38xco053461cq","zkWn3RSSiChTQL0HTcDDmLnT42gwDi0QRXQfwwS7WUk=",-8872425683257256437,-2199441322532398635,1382046787439005105,6498188997252203931>()) {
                        case 302365375:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }

   private static List<e.a> P() {
      ArrayList var0 = new ArrayList();
      Iterator var1 = com.yiyiaddon.h.e.c().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"suuym4y6dtqlp","DNYRsFm0yDkmCG/HokssfbnEAVNx8e2xjwGy8rFE1Tg=",5610544848745655698,2901240746281333278,2478724657867852158,758428901146657379>()) {
         case -1122807838:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s12m8htzp8n5a8","C3noo5tGBwXXN4myipJZ/l3v5dreeDavtOZ07+VMsb4=",-6747427669463229574,-2606762053496208432,-5983022477674969940,2757708912871643951>()) {
                  case 806418609:
                     com.yiyiaddon.h.d var2 = (com.yiyiaddon.h.d)var1.next();
                     if (!var2.ar()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2s932l4gfef9e","/cmTw/5Bcv/nBFFXIaL90O/wLkOVp6y69pxZ07jpyA0=",523436829519679128,-7870982734811754022,3595384849796769394,-964316988945717174>()) {
                           case -1908714630:
                              switch ((int)com.yiyiaddon.m.b.a<"s39yk3tsm37agz","IQKBYg9ohOmZ9jpUh5oklb2cFUaV2AznwDgvcvv/NJc=",5284168864485371961,4387776950800786946,-4804609161049625335,-4927434615585944076>()) {
                                 case -353370387:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        com.yiyiaddon.d.b.a var3 = com.yiyiaddon.d.b.e.b(var2.s());
                        if (var3 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2475kyuqwmvnc","6u6qN+M46OiCpkMNagNerynUNDrb6jnFdP8ZorsMt/c=",-9212102566097531830,-5302820074833485470,156350473703834469,-2054765343567386482>()) {
                              case -1518167807:
                                 switch ((int)com.yiyiaddon.m.b.a<"sqmlu4jnl0gqs","tP5rHjQVvVJjxdAbne7JnfZn1HepPGIhsRthkLGwnRk=",5026341545448327660,552582654324583059,-6506161006139072645,-4830743875914982472>()) {
                                    case 782683995:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           String var4 = f(com.yiyiaddon.d.b.e.b(var3));
                           if (var4 != null) {
                              label33:
                              switch ((int)com.yiyiaddon.m.b.a<"s20frssaywov3u","B4Q7xwmEbVjPWcH6K0ViORdTBR/IPLUHpAtKyCR7xeE=",4488092044779878580,-3593358986767482734,-4892911921807265963,-4766520198681837146>()) {
                                 case -867241803:
                                    var0.add(new e.a(var2, var4));
                                    switch ((int)com.yiyiaddon.m.b.a<"sx1fb5wg113d5","GtOP/FH1eBoFweehgb6CmEsIQmQVbeJMs+x+yIBB9G4=",-7920638668574595517,700973680858444244,-1769039506351296752,-4471920816885695556>()) {
                                       case 505485445:
                                          break label33;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2ekdxtobfcw35","q7WVyQCZm6JkjXl1WROZb57Ht6Z6UNixLeU+qgXX41w=",1544740915377126208,4040919120848366288,4824049192466654122,6094068437974356160>()) {
                              case -585159671:
                                 continue;
                              default:
                                 throw null;
                           }
                        }
                     }
                  default:
                     throw null;
               }
            }

            return var0;
         default:
            throw null;
      }
   }

   private static String f(List<String> var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s234ub5fpiql21","W1hjxCVdkwH6dyD9doy566X2DIh9lxUS01N9CfCogRs=",4473107672074875328,622825281291057831,8113648606552635511,7422102824444973923>()) {
            case -1054160176:
               if (!var0.isEmpty()) {
                  Iterator var1 = var0.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s17jbxootyucgf","SYpW/VEksZPNUYkq7KUFZD/EEcgkWPrpJwQ2P53NhDU=",6941990048336755813,7313516433051539498,-7238340353044600761,-6420424040241585660>()) {
                     case 901833350:
                        while (var1.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1aj9andqunbol","vUmmBfiROPXDGqahYuSQTfcBMCp9CnKaSQ4xrauFrmc=",-5912427989758340414,-2883552089496777057,-7619517864613204857,6096857756668584912>()) {
                              case -242346682:
                                 String var2 = (String)var1.next();
                                 if (var2 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2hk668oyiiqw7","I3At4ignWjYsS3m1XDi80IlPZLN7Zev036Z3s0J/ueQ=",6150935828734921851,4124845190143458636,4520202971969607377,3063766731322463727>()) {
                                       case -1961347087:
                                          if (var2.isBlank()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3omplngp8qw8x","aQALhWJF5DSNTixKzWHp792PZrYod+DcD0uoXtrSfgE=",-5245964299497775470,-6736307156155090631,6026772088852138582,6974967344666643956>()) {
                                                case 627216743:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3sa10oiwkyjw9","iw+tTRrgVhpKlAP3Rlqfoe4CP64jrkCoXNxjkC779AQ=",1937459424874190035,-2330824900010278601,7734937153964939814,-3651230585926068100>()) {
                                                      case -22790752:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             if (!var2.contains(
                                                (String)com.yiyiaddon.m.b.a<"sbvohjgvilrpc","sasvQZDL161BDqXquad83/xdQwirPnNxbM6ReL7ieLSJZyRY16o=",-4893982934130330016,-2218648752645817364,2898678320999128297,2517683772888362835>()
                                             )) {
                                                return var2;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s3lhvzrosnvuxu","l72GOwPxceYZn8a3wgEoasjGTylL/RAN4AApki5P/xc=",5700265376064659280,9086296818774449529,-6479427009987511709,2438939481737288139>()) {
                                                case 341833649:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s7bzgzfx76gp4","K0VeNs6aWp5NMcsHisvxTb8HqppxdDwSEHxoa0TMl4A=",-1083995952203031109,-2531055081041310732,-5335289959301598959,2603237302885535004>()) {
                                                      case -143362390:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        return null;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sgr5pcju5sse6","UGAg36qrlCYrwdN882gnT2m3R06KGuv8YXb6IKDIj7k=",739086511796361954,-1717571351228737035,-6546127116291678796,-4837731407095087655>()) {
                     case -1630522498:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static e.b a(com.yiyiaddon.h.d var0) {
      if (com.yiyiaddon.d.b.e.b(var0.s()) == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dvigun0zp620","z/mH8f+FGcxxa+DVwm6tT6Ud1s9O2/bA6cUGiooT24I=",-8374564540771272853,7255922795985773183,-274413577129029331,-8684066987454421051>()) {
            case -1944342930:
               return new e.b(
                  com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s1ykpa851rx794","fpq3HbI5y1fuNxPK5YgPh2lSTZ1n7YTNzakUfLdq/bGsqw==",-3591454876639894601,-8004546257474928315,6602075651214863538,-351001095061365070>(),
                     (String)com.yiyiaddon.m.b.a<"s1a3xoxo39br6n","n7Z+bawBZJcNQQgYIUbSdDV6xVQx110qG1KWreu7FH+BfsJA5ZWytoKtf0eEXM00VMRuB/759Yk=",7253255491206968935,-8469903860098967691,3385044275077795211,5006902010386497878>()
                  ),
                  (String)com.yiyiaddon.m.b.a<"s32g567204ilt9","UkBJq8tM4KJok7nsvAd2kbPseyMupvxkyXYy6w==",-705948314504226962,-2259136850606762614,636203735549940144,-8445967492709934357>()
               );
            default:
               throw null;
         }
      } else {
         if (var0.s()
            .equals(
               (String)com.yiyiaddon.m.b.a<"s17ow5npxmo1t9","KyvXNbIbtMxU/uzsi3068UeAd4edm8RXfdb/AX0n8Jtcgzj9A/71dZ3V",-1596937450875040463,7609656307386103838,3318412744535003942,-5685704006702905547>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s1mwlcrtbkp591","VYmz383HbJ+OXXg8/y6kr5sdZQIQmvUaf52Vpmupi7o=",-9068499681186698461,4789652293898778546,8695924094784599075,-6379279663181685127>()) {
               case -1915779069:
                  com.yiyiaddon.d.b.a var2 = com.yiyiaddon.d.b.e.b(var0.s());
                  if (var2 instanceof com.yiyiaddon.e.n.b) {
                     switch ((int)com.yiyiaddon.m.b.a<"sri3q3ut0anu4","r4aWB3BawlXQJ01MxQBAo4y5GvuIZvpjgeF+x6jQkic=",4744126800938582595,-8396624610859866333,1555360509861114673,-457339691793162225>()) {
                        case 2131628892:
                           com.yiyiaddon.e.n.b var6;
                           String var13;
                           label110: {
                              var6 = (com.yiyiaddon.e.n.b)var2;
                              var10 = var6.a().b();
                              if (var10.ew() != null) {
                                 label76:
                                 switch ((int)com.yiyiaddon.m.b.a<"svoro74c1dq5h","LcYOJUJ9ofZvqAU+TQTfTfA4SKLEKmRHAj2T3NGPHdE=",-2109096431933957900,-2391874810201472497,5228616564962163543,719741106971402570>()) {
                                    case -1419018874:
                                       if (!var10.ew().isBlank()) {
                                          var13 = var10.ew();
                                          switch ((int)com.yiyiaddon.m.b.a<"s12qjw631zwkv2","Hx0ZMKormjpoAAPgbdC9omPalmzcCmS6uHy/T8ttHYg=",-526252490222351564,1784410824519431614,9089270667222523902,-7950487731793750044>()) {
                                             case -1442472527:
                                                break label110;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s3a12ue2oz017t","Hwi58/m+CLzWjGmOTQnJHvRHm2tiRK2YxFqxBCbzCJM=",776687231901149410,5597444567225584014,-6950811265556428434,-8170732978635118858>()) {
                                          case -162251416:
                                             break label76;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var13 = com.yiyiaddon.l.a.w(
                                 (String)com.yiyiaddon.m.b.a<"s75e1fabqt7cf","MRwXcOoCpFX9XDp/GiwNRY8GCVHvhV+AGfhbyil15a0=",3540123451044406251,919986351262359662,-4658269862306548143,-8439731956431670465>(),
                                 (String)com.yiyiaddon.m.b.a<"s1v2jmtsrvq6uw","lT5jwZ7tmYnhZD3BgIZI0PDWnppdP7PNaSErpCbpLPTyPYCo",-7472139924401525245,-3503917266736160355,-8237248947974128735,-4567472020661450859>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s2ozq9ms5zjrhq","REuXtS5jM2D9CizCGTC0cJ0oMm7AfTkPBALODOaQAtw=",-3039193091612221998,6649193190664662225,995656929894745645,-9179097164038822501>()) {
                                 case 107943886:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           String var11;
                           label103: {
                              var11 = var13;
                              if (var10.B() != null) {
                                 label68:
                                 switch ((int)com.yiyiaddon.m.b.a<"s235cklg4ac667","4pGB8hJsJrrtuzriJoBI9Jpo9lHAwcf4QtKEQuuE1vw=",-7420143955143219554,8678433181522922350,-4604370564734007381,-8019218878885881459>()) {
                                    case -1829781022:
                                       if (!var10.B().isBlank()) {
                                          var13 = var10.B();
                                          switch ((int)com.yiyiaddon.m.b.a<"s3hx7qu21pzbx9","aYmRiyuU06ZFQPISBbXZfAX6i3BxSiBkQTSjFMRzgcA=",-6514500831853023753,3043157028565078574,-3965357863221343316,5861913199680180395>()) {
                                             case 2018059838:
                                                break label103;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2z1rhr40ftl6i","ZckUvOZCmJRp8X1X4IpKEfN1TgO1NjDltoF7VK7gKFY=",4314241696405562698,-6653500534405392378,1826819441018499062,4841502499015934208>()) {
                                          case 438830560:
                                             break label68;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var13 = com.yiyiaddon.l.a.w(
                                    (String)com.yiyiaddon.m.b.a<"s1mohofz5y683a","W6BLONHL/SIVzNAC7nNzHIMis7AV1HKkE4IkdYQBuxEWNDX4",-6752879386593139379,-3305026248774634267,-5237964339747253691,2120152581950211811>(),
                                    (String)com.yiyiaddon.m.b.a<"s3a5168chffpso","ouUQQuGG1f7vLVyCn44HKRmZBWO/gESlSgx5HbU66IBnADO5ti1ABdD+ZSSU6xLVcm1ZvJUNERg9gJyg",709349484516814850,532718087933019415,5262281068528697800,-8538198843894170503>()
                                 )
                                 + var6.aC().size()
                                 + com.yiyiaddon.l.a.w(
                                    (String)com.yiyiaddon.m.b.a<"s3g27knhsve6zk","ikwJgo7gI08eaEGqqaTWlJk36VM1KqVEz4+/zrYsE/gtJQ==",-7254942375368939170,7713320513659102505,-2200532364885157385,-6155113155131874433>(),
                                    (String)com.yiyiaddon.m.b.a<"sqk3udo0y6epg","BHVDsIa7timVpI7k8o3vMtqVA8cWuSVVmAf/Uv+9uCqI5ycVD1rIEQ==",-7274734890126402072,-5013759132652051124,-944594493904334412,-1624531245365104397>()
                                 );
                              switch ((int)com.yiyiaddon.m.b.a<"sj1ku8gw0g1o8","AMtx+CpTo+64+VUuPWhXwhQUrgXFcRKuUHfBJKbw2Pw=",6481036309173880411,-1180721270935695244,-5481694156529880330,-1988782733012585156>()) {
                                 case 2126604382:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           String var12 = var13;
                           return new e.b(var11, var12);
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (var0.s()
            .equals(
               (String)com.yiyiaddon.m.b.a<"s2wu3wljw6x2gc","0H9dSLgTzK3Pflxs4jNDt3xdS4nlKSS/TekgL3hGyOw8z0vdDh+VC0EPMZ+8Nw==",-4922545052829756194,-2656238730902748277,-2321339591726786945,3549336883441079051>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"sfzv7wwbykzo2","RjVkUIErOTzx2KlHGMn1Am3YOvl0zYZ+B6VVzI0a0NE=",4710901208876740228,-4090357735063470484,-3224127604020168950,6383459179055223846>()) {
               case 1367217954:
                  com.yiyiaddon.d.b.a var7 = com.yiyiaddon.d.b.e.b(var0.s());
                  if (var7 instanceof com.yiyiaddon.e.b.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s22jjzmmgdou4u","a4YA+uAGllmUvLgAYTbrCEO+FlF2nhcDRxO9FZgkCG4=",-8093527434706942209,543936053161793830,7652361194855589088,1831810661302641946>()) {
                        case -1145897688:
                           com.yiyiaddon.e.b.a var5 = (com.yiyiaddon.e.b.a)var7;
                           return new e.b(
                              var5.a().a().toString(),
                              (String)com.yiyiaddon.m.b.a<"s32g567204ilt9","UkBJq8tM4KJok7nsvAd2kbPseyMupvxkyXYy6w==",-705948314504226962,-2259136850606762614,636203735549940144,-8445967492709934357>()
                           );
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (var0.s()
            .equals(
               (String)com.yiyiaddon.m.b.a<"sce54i7uebgtu","SDxfZeaA5mnJi4usxIcYs7YRYRETkN8GpYM0j5sMcrJC/em+PJJnlEqC8Ly7CFTvwPU=",-6167755815586998207,4243107108053663861,-7553353613857801726,5966213360741726195>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s2bxgfzepvdags","WKiSfvJmSu0wO7BonCHMu6RyC0mq9EJKQ3W0zFro3M4=",-4083997078129129146,-6194533316205632626,-4762823088838582537,6091156234226362761>()) {
               case 819103161:
                  com.yiyiaddon.d.b.a var8 = com.yiyiaddon.d.b.e.b(var0.s());
                  if (var8 instanceof com.yiyiaddon.e.h.b) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3d72a8mmeu5y8","0YQgmUCGRZ5Nr6VyHG79Cfq4Z/3/OHPjAfhWSOETu5Y=",6148357558496126120,4045441359238937275,-5168074712493990896,401947829240177354>()) {
                        case 377834067:
                           String var10000;
                           label119: {
                              com.yiyiaddon.e.h.b var1 = (com.yiyiaddon.e.h.b)var8;
                              List var9 = var1.aa();
                              int var3 = var1.aK();
                              if (var3 >= 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s218nlix297823","o1Yo9TvFAlu/FCxy5Q/SYakNAhSfV6i9ZXXbOSONFqM=",7988976087359233335,-5801416120777318866,5717033899193634217,-2754080248505815809>()) {
                                    case 993490547:
                                       if (var3 < var9.size()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2ozx9twzo071h","9CqZgU5VKSbxfeF4j6Pb/l6JZ+Am88PK1b77lgC6ocM=",-5516050539978346889,-7182976414923897466,-2579646645684855853,-8789530085459954212>()) {
                                             case -2012522651:
                                                var10000 = (String)var9.get(var3);
                                                switch ((int)com.yiyiaddon.m.b.a<"s20mqbmpxkvblz","7Ak+AhBDXmCvSHm0JhV6RfY4rDTANJu4dFeXGW/eu4E=",8795762683233403187,6582769035309434397,-7112740999116432845,-5148349690290203097>()) {
                                                   case -1921173839:
                                                      break label119;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              var10000 = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
                              switch ((int)com.yiyiaddon.m.b.a<"s3g1wvugibrzl9","lmIrQa0ErOJag+kjYhu8yJyL5nbSVoqgeHgtYt8sCxY=",-5744767219468019589,498880085576219400,3261325687683517110,1742754409410077381>()) {
                                 case -800613798:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           String var4 = var10000;
                           return new e.b(
                              com.yiyiaddon.l.a.w(
                                 (String)com.yiyiaddon.m.b.a<"s150wrmxq2vw3l","dpwzhCSUAtcKhJm2prLDAPGrSzJkon7eAKcQsdr2+ZfX2g==",-3698186787622234494,-1071875312349427206,5509076179608845976,-3996555532953513507>(),
                                 (String)com.yiyiaddon.m.b.a<"s1vly7mdod9pyk","Am2RvecMYlpbpQ2esCNKnBQLg/nC4XPsdu3HwKv9XIAm0baIi2yE3m9d",5940320817798827949,-5688033632451005265,-6061041466577371910,6110191779393246169>()
                              ),
                              com.yiyiaddon.l.a.w(
                                    (String)com.yiyiaddon.m.b.a<"s1op232a3tiqd0","XfqTUy+LBGQbzhWI6FCpLpJW23p3nfiXj4QncdOXQR84cWrHET4=",-490164562118422832,3887049792222656789,3380726012514125940,8967479813776339725>(),
                                    (String)com.yiyiaddon.m.b.a<"s1om5q7dcyjc3i","kXkzlXJjHRB2DtNjWw41rTa47j6lCb4n8LDkusW7NrkdQqqGf6CwzQ==",3603823440194240499,-5852043533543769687,8192267788681814794,-6358305505565588138>()
                                 )
                                 + var4
                           );
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         return new e.b(
            com.yiyiaddon.l.a.w(
               (String)com.yiyiaddon.m.b.a<"s150wrmxq2vw3l","dpwzhCSUAtcKhJm2prLDAPGrSzJkon7eAKcQsdr2+ZfX2g==",-3698186787622234494,-1071875312349427206,5509076179608845976,-3996555532953513507>(),
               (String)com.yiyiaddon.m.b.a<"s1vly7mdod9pyk","Am2RvecMYlpbpQ2esCNKnBQLg/nC4XPsdu3HwKv9XIAm0baIi2yE3m9d",5940320817798827949,-5688033632451005265,-6061041466577371910,6110191779393246169>()
            ),
            (String)com.yiyiaddon.m.b.a<"s32g567204ilt9","UkBJq8tM4KJok7nsvAd2kbPseyMupvxkyXYy6w==",-705948314504226962,-2259136850606762614,636203735549940144,-8445967492709934357>()
         );
      }
   }

   private static String[][] a() {
      com.yiyiaddon.d.b.a var1 = com.yiyiaddon.d.b.e.b(
         (String)com.yiyiaddon.m.b.a<"s17ow5npxmo1t9","KyvXNbIbtMxU/uzsi3068UeAd4edm8RXfdb/AX0n8Jtcgzj9A/71dZ3V",-1596937450875040463,7609656307386103838,3318412744535003942,-5685704006702905547>()
      );
      if (var1 instanceof com.yiyiaddon.e.n.b) {
         switch ((int)com.yiyiaddon.m.b.a<"s2us0yqlg14j6x","pyWwQCwZ3GLfKQzSkMwlGkH9x0W8Kdcb5aWxIkgnNJQ=",-3174838273393249470,-8654021063993797428,-9097370277600817599,-6652849514718548471>()) {
            case 1301847064:
               com.yiyiaddon.e.n.b var0 = (com.yiyiaddon.e.n.b)var1;
               switch ((int)com.yiyiaddon.m.b.a<"s32g2ho0nw3zuf","jI/lmVd+kEozQ/8SADERplIsV+pwXkOEEiJstNOJnRA=",2184308329943617218,3457596704857290379,-146640359571486993,1703776133483364312>()) {
                  case -6239044:
                     com.yiyiaddon.e.n.q.c var4 = var0.a().b();
                     boolean var10000;
                     if (var0.a().a(com.yiyiaddon.e.n.h.d.WATER_SOURCE) != null) {
                        label35:
                        switch ((int)com.yiyiaddon.m.b.a<"shgodryvonvlr","5f0jeriUiVLdKcsBZJJckzQHGOGe/+VmqnE4eOmKa0A=",1373467069336626499,-8416926945847866880,8649004226615841223,-1765903045770597639>()) {
                           case -1380120621:
                              var10000 = true;
                              switch ((int)com.yiyiaddon.m.b.a<"s3b5x9fpnk8aa1","SXpuHuj7cA87LpBxcgMkOe4YHp7UJd4EvqGnyYlR4sg=",8163781599901716629,4309721485379079320,7098229581035011342,9003132530445125190>()) {
                                 case 1791563828:
                                    break label35;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"sbmvsg7cxpeaq","K9wPC9M84wurGCd4GbVQrzGnmdOpHl3yJznNaCswzm0=",-2093238502998293246,6771969852180003284,7772459734964329090,5984536759261861264>()) {
                           case -1805230958:
                              break;
                           default:
                              throw null;
                        }
                     }

                     boolean var2 = var10000;
                     int var3 = var0.a().a(com.yiyiaddon.e.n.h.d.SPRINKLER).size();
                     String[][] var5 = new String[][]{
                        {
                              com.yiyiaddon.l.a.w(
                                 (String)com.yiyiaddon.m.b.a<"s7pph40jp640y","BVoYspgW+8R0z5LThvh36GphfCeg5id6m2zGGTbhuvtJdM/ZYgU=",8995455594508394443,6416768500315552214,-1429629313196512202,-8059127581329606411>(),
                                 (String)com.yiyiaddon.m.b.a<"scew3aq0ephte","6vBqMewVUsflopvVryitMf0CDyoUWcE3IlWPY1DEYIhf9vzFDbs=",1779641666194927456,7571820538763707200,-129837726221545676,-706997144568049602>()
                              ),
                              var0.aC().size()
                                 + com.yiyiaddon.l.a.w(
                                    (String)com.yiyiaddon.m.b.a<"s2blioh38s5434","7VwHZ8EdB613pj7OdEWiQhnSJut02SlHn1T+M60IyUM=",3963498401478805570,-4386986917551320435,-1583065160365746150,5234648627813391864>(),
                                    (String)com.yiyiaddon.m.b.a<"sqk3udo0y6epg","BHVDsIa7timVpI7k8o3vMtqVA8cWuSVVmAf/Uv+9uCqI5ycVD1rIEQ==",-7274734890126402072,-5013759132652051124,-944594493904334412,-1624531245365104397>()
                                 )
                        },
                        {
                              com.yiyiaddon.l.a.w(
                                 (String)com.yiyiaddon.m.b.a<"s3apk4wnzge8lj","g25KUk8YzVsapoAXPUHF3qJOoqPiB8Fro5IXoNazvyoxvUTO",3344799803374238610,6454148053932029689,-4785657398665888738,-1458018379161870900>(),
                                 (String)com.yiyiaddon.m.b.a<"s22whdrwz4o88v","HT0ZtxlsCIDJXfHDi08U/eZDIHc/z2XOeB4Boo64njgfvKqAvtk=",7756310293110927076,-5346502728333452916,996327069072150374,-3826072525912650867>()
                              ),
                              cl(var4.eu())
                        },
                        {
                              com.yiyiaddon.l.a.w(
                                 (String)com.yiyiaddon.m.b.a<"se462ju82n0d7","sajBVLyXve22RKKWPNK+XrVeNx+S3SySbRAWsLUSSOY=",7695608205477020214,-3648028488161658593,-9136215699953419794,-2913050967001408435>(),
                                 (String)com.yiyiaddon.m.b.a<"s1mo5xxbi7psxm","DAiv2t3F1lfbG4kFUSCLGJL2mxS7KdSzSEx/WY9de02us6ovK8Tk8w==",3305645027428509218,-1628775624750203591,8593955029684305744,3496468181140308824>()
                              ),
                              cl(var4.ev())
                        },
                        null,
                        null
                     };
                     String[] var10003 = new String[]{
                        com.yiyiaddon.l.a.w(
                           (String)com.yiyiaddon.m.b.a<"s3aseyd77wkmad","BGUlsMQZFR4biO91XSXurjwoXZtgZ196VTf+sknLq5WI4w==",-7193583776823096942,2567780953954335004,-3147368653745428592,-5942818254820832905>(),
                           (String)com.yiyiaddon.m.b.a<"sjquqlrw9i020","knWvdfZU/gXpPS2Vowuz+OcEyQL/tTzn4mumUrfQkrxvPO8181l6jVjQZg8MBfHziN6VQg==",-5262573759866174571,-5545944857506049972,-2359038812010942414,2983611010803784106>()
                        ),
                        null
                     };
                     String var10006;
                     if (var2) {
                        label28:
                        switch ((int)com.yiyiaddon.m.b.a<"s1l74221scu24n","3haLTxKwlJCTTSvYTTIdIbYEmLY9fpsNSvnDFojMPno=",-5805598617182409322,-8973432607055780755,-5977322001964828103,3630528685597345655>()) {
                           case -340157089:
                              var10006 = com.yiyiaddon.l.a.w(
                                 (String)com.yiyiaddon.m.b.a<"s3b6e0d5p0qg3u","p7rSDDqEeMaybnS9CfdAJlYe7/rBDSeXBXZOO0PptRND9A==",-1213674431562307411,4834753953783688997,-7758057156621577618,6532442402683562726>(),
                                 (String)com.yiyiaddon.m.b.a<"spq37v0tm89av","TlgJTwWzkZXFeLR3PJJj5FPW9UWJeTxRBCmp5PjdkITm0d+2QGw=",5086200451129216583,-8874338558111814352,6362363229030398136,803567706627737850>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s1jujm4udrz5uq","lStSrFnCmE+qzgvBn7/MEaeCV5TcMYHYDJlnOZuge5o=",4843903872859046798,2931317610833565352,-8655461220511320198,-1015755376034958815>()) {
                                 case -713524062:
                                    break label28;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10006 = com.yiyiaddon.l.a.w(
                           (String)com.yiyiaddon.m.b.a<"s11i1dt3lt7y77","XTsAM86xMkUjqaJ8ciXhPwbe6+y6DkcUhRfs6Af6cnf2JQ==",-155047439762812250,-3612474932744884624,747669681258890910,-5190316049245377903>(),
                           (String)com.yiyiaddon.m.b.a<"s1cq0km9hr5iqn","h0AAgg6aVnVfG3VO9vEgKQKA+GiHK2USO1donndSmYE+h89xp+NqSrheoY7Iiw==",6934181673314299904,-2590324890852917602,-3069181234837950406,6222078115807772124>()
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s3oest05j1gigv","VeA3zkZ55+pPvv+1FRBSZ1HtRQNNF8yaSIAjNUG47AI=",-7919332076384643533,3146035074697376318,7163672533769737970,-121362345536985880>()) {
                           case -1400321340:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var10003[1] = var10006;
                     var5[3] = var10003;
                     var5[4] = new String[]{
                        com.yiyiaddon.l.a.w(
                           (String)com.yiyiaddon.m.b.a<"s366td3al08lxh","aq81QnFecQ/1smJLP0lFeCgtzLJw1CAJPlAZwnT1pCarsw==",-2521761377533358668,8573420401035280247,-4129009759869759456,2881181922328978951>(),
                           (String)com.yiyiaddon.m.b.a<"suw7x8n3rr7fd","jjOSrTjI/sAtaelmCqbn0FCA/F+4CaFzYyz8KVOU+UUxp7YiLCG42GsK1aTFqZXE",-1921846035892883621,-358269613635889460,4033784926364339996,-4677722030161851754>()
                        ),
                        var3
                           + com.yiyiaddon.l.a.w(
                              (String)com.yiyiaddon.m.b.a<"s2t5nrkyanj90i","dLv5QN9u5i4x8g9A3GRJHiAcBzwHkFHjB/8r877LL9k=",523388312772052061,-2021968057702621508,3050490465831222296,8744497262884469520>(),
                              (String)com.yiyiaddon.m.b.a<"scjzoy8ritmpu","lL2vxI/a8M/xD4px9BPoJUABq1HVARuw7vF8NxVoKJApnaESM25zNA==",5396557058341249282,910360342047426501,-3431275326007354285,4715308026776324848>()
                           )
                     };
                     return var5;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return b();
      }
   }

   private static String[][] b() {
      String[] var0 = new String[]{
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s7pph40jp640y","BVoYspgW+8R0z5LThvh36GphfCeg5id6m2zGGTbhuvtJdM/ZYgU=",8995455594508394443,6416768500315552214,-1429629313196512202,-8059127581329606411>(),
            (String)com.yiyiaddon.m.b.a<"scew3aq0ephte","6vBqMewVUsflopvVryitMf0CDyoUWcE3IlWPY1DEYIhf9vzFDbs=",1779641666194927456,7571820538763707200,-129837726221545676,-706997144568049602>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s3apk4wnzge8lj","g25KUk8YzVsapoAXPUHF3qJOoqPiB8Fro5IXoNazvyoxvUTO",3344799803374238610,6454148053932029689,-4785657398665888738,-1458018379161870900>(),
            (String)com.yiyiaddon.m.b.a<"s22whdrwz4o88v","HT0ZtxlsCIDJXfHDi08U/eZDIHc/z2XOeB4Boo64njgfvKqAvtk=",7756310293110927076,-5346502728333452916,996327069072150374,-3826072525912650867>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"se462ju82n0d7","sajBVLyXve22RKKWPNK+XrVeNx+S3SySbRAWsLUSSOY=",7695608205477020214,-3648028488161658593,-9136215699953419794,-2913050967001408435>(),
            (String)com.yiyiaddon.m.b.a<"s1mo5xxbi7psxm","DAiv2t3F1lfbG4kFUSCLGJL2mxS7KdSzSEx/WY9de02us6ovK8Tk8w==",3305645027428509218,-1628775624750203591,8593955029684305744,3496468181140308824>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s3aseyd77wkmad","BGUlsMQZFR4biO91XSXurjwoXZtgZ196VTf+sknLq5WI4w==",-7193583776823096942,2567780953954335004,-3147368653745428592,-5942818254820832905>(),
            (String)com.yiyiaddon.m.b.a<"sjquqlrw9i020","knWvdfZU/gXpPS2Vowuz+OcEyQL/tTzn4mumUrfQkrxvPO8181l6jVjQZg8MBfHziN6VQg==",-5262573759866174571,-5545944857506049972,-2359038812010942414,2983611010803784106>()
         ),
         com.yiyiaddon.l.a.w(
            (String)com.yiyiaddon.m.b.a<"s366td3al08lxh","aq81QnFecQ/1smJLP0lFeCgtzLJw1CAJPlAZwnT1pCarsw==",-2521761377533358668,8573420401035280247,-4129009759869759456,2881181922328978951>(),
            (String)com.yiyiaddon.m.b.a<"suw7x8n3rr7fd","jjOSrTjI/sAtaelmCqbn0FCA/F+4CaFzYyz8KVOU+UUxp7YiLCG42GsK1aTFqZXE",-1921846035892883621,-358269613635889460,4033784926364339996,-4677722030161851754>()
         )
      };
      String[][] var1 = new String[5][2];
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2h9oo8fzaax74","1PyYN4KsTDUGxnK8daqSKT/X5+fwM6X9gLcbBR55GPM=",4017267044514551623,6344108908246284593,4356108888637011751,-2344651868835780819>()) {
         case -711647969:
            while (var2 < 5) {
               switch ((int)com.yiyiaddon.m.b.a<"sz0ozesokxgd0","mTVeebrF2QGzh2Uk1H1NW09ZhF5zZxVvL2mYWUzKUvs=",-48969617761931373,-57476660876810178,-7800560638362257850,-4304000667963107026>()) {
                  case -985145574:
                     var1[var2][0] = var0[var2];
                     var1[var2][1] = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3r5v903vlmde8","9SSl0F1zr5axVzG2y4a6TILr+/OCa8p5bBAzVfOQuIk=",-8112845603556409461,1987138727946157612,-5020271204737923369,3674514499446474761>()) {
                        case 1001205986:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }

   private static String cl(String var0) {
      if (var0 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s3n4dqun5i0ylg","k8BQfcG41NPUSiaFVZlFOE4vqrC8+K1A1QsJZXi3DE4=",-1832780783678701877,2238095210718091182,-3130693306793323766,834181744329991985>()) {
            case 240953813:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1fg1mzx3hshu1","AiGBae4XeoOmd3pAIIu9wAJl1DG19R9cAwkWE+gHs7o=",-4519429638416466650,7515745999547913302,4983922755702669742,-3718271433766693242>()) {
                     case 1381474977:
                        return var0;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"snhftc7myvcqx","C+XZsAoi7wW0SLTnzXAjhdSnnCo57VCHi6JVL2Dci5o=",3276971360995874026,2525025266454090731,-1731838685726489357,4809832302912095399>()) {
                  case -1081347283:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10000 = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
      switch ((int)com.yiyiaddon.m.b.a<"s12ohtk07fgwmv","hC8o4FudJ8w8e9Ln+nWM40YlKaL7nOMxdHgumG66XhE=",205903492075528097,8236898024105758442,-6735648445306724146,3188379733929927630>()) {
         case -821741130:
            return var10000;
         default:
            throw null;
      }
   }

   private static String gF() {
      Minecraft var0 = Minecraft.getInstance();
      ServerData var1 = var0.getCurrentServer();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s9lvrovuiqvuf","u9edUSfRpGXzlSHVlvPzsM92XCBXyXgM8SOQCFbE9Ew=",-319726603484071370,3832241513207420066,1078082224527391667,1662920953565123862>()) {
            case -44343175:
               if (var1.ip != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"seuq1pcqft0v4","XW2vB6jNlCjy99c8ge3qq806HfTK8I0bHlNvJEmMLEY=",-1258685710877428972,-2251023198953576664,-4192144249222504113,1006934004937302640>()) {
                     case -209792136:
                        if (!var1.ip.isBlank()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2xc993zu8lk5g","tdOt9WGwH60HOYXH/zRye98atltdFJrntJ8X+hYoF4g=",6884029851730830792,8487218181728696410,-2886280461679673168,8644238587334619930>()) {
                              case -1177076958:
                                 return var1.ip;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      String var2 = com.yiyiaddon.i.g.c.V();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ipuomvz8xq5n","zLC0s1PeTjn4Tcg9JIw1qmKVetqs30ubxuM1x5ama7M=",2849119420182732332,1260335886199712364,-7537023339899614562,-9182917681455856835>()) {
            case 11700960:
               String var10000 = com.yiyiaddon.l.a.w(
                  (String)com.yiyiaddon.m.b.a<"sqfd5nr02jd2o","Xh5S5L9ZT48hXr50Y5HzRjDJe5Y/xLTjUYZmGD5P3ts=",620730032317863602,-3739052613074471013,3896539294378922604,-6017355900061205732>(),
                  (String)com.yiyiaddon.m.b.a<"s35cs9e3wg4rc0","zripNNgy+9lj9nueHmTXIEEiWlbwWg9xX2hAZmkECVwNXTv9EeCe6uLG5XtuNqjhINOVjw==",4897071495810200407,1113671925337043173,8704034760231993522,7275693183847892443>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3iu8tnxqtlkft","p3+vjuxQDFGWCE2w5R9e3xUMKEHFIAJW7Mn7tTspK5o=",-2762353936676298454,5240115764782661748,-1684959898050628469,-3667175920992995579>()) {
                  case 578092902:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var3 = com.yiyiaddon.l.a.w(
               (String)com.yiyiaddon.m.b.a<"s32lqqwdtycxzg","UFdF0r7X956/fcyyYDk7JTvdGUNjC3l1YcRfCwnzMfuxaGolTkM=",-4163994724502094969,-7042871390258301610,-5312103992699940236,9110001260486340050>(),
               (String)com.yiyiaddon.m.b.a<"s1fbgy8hb9d7fg","65q82SEX7suvo3wsc2l3pot31BjV8A+pzjM5Y9fMm5ue5GJYj4hDUXkf8R7DUneaCcSrBJrP/Dd+IQ==",1041649227829283528,2031339471216482471,-8523650704652755245,-3849236439992531478>()
            )
            + var2;
         switch ((int)com.yiyiaddon.m.b.a<"s2dvmg33vrbn0","0+ZtVNhTbw4TeZ4QZx15+H5+eU4SJp3qoOYYBR/HQGA=",2554683434226111702,-8580646683929518689,7055148984638487801,795470818580261062>()) {
            case 1706765377:
               return var3;
            default:
               throw null;
         }
      }
   }

   private static String gG() {
      if (Minecraft.getInstance().level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qzbwrcxpjvzh","MW7w34UYmLe2VX0f0JV23qc0idbXNj+1/VbGXKscLZU=",4564386064421353450,6700129340761558975,-6663633232420342946,8642878106466669556>()) {
            case -2094048077:
               return (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.i.g.c.bU(com.yiyiaddon.i.g.c.bU());
      }
   }

   private static String gH() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sdtje2xtciqyz","WseMxcFUMyh/k06uG58lMulchg+dYVdifJWbR+vxWgA=",-943375641363843119,-2236895208491235753,-2162669741069304766,2673762705366576228>()) {
            case -1830263891:
               return (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
            default:
               throw null;
         }
      } else {
         BlockPos var1 = var0.player.blockPosition();
         return "" + var1.getX() + var1.getY() + var1.getZ();
      }
   }

   private static String gI() {
      Minecraft var0 = Minecraft.getInstance();
      int var1 = var0.getFps();
      if (var1 <= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bcyfi2bk5jtb","aoxxtHjM6HarAtXUfJYtH/YhBEB1+2NMWDXMhFofxHE=",875417070403557422,-931441156281490873,7624425144835706260,2245419813674540184>()) {
            case -1545088940:
               String var10000 = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
               switch ((int)com.yiyiaddon.m.b.a<"s3hzr97jfe273k","60ZPvTT7x6mwx7FWNF6MAV7TnvttsH36rQBed1Gm4rU=",-4051447436432623634,9136966226146772660,5418122243921072355,7875130361074576707>()) {
                  case -1452674625:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var2 = var1 + "";
         switch ((int)com.yiyiaddon.m.b.a<"s3eha53jvko8qu","WstjIHVbqRpb8Utad2fjcSHWV/E4vunH+MwjeKhu9lQ=",4459355468237463591,-4081058578113813608,-2502984553919929250,2222309369332623909>()) {
            case -2088295763:
               return var2;
            default:
               throw null;
         }
      }
   }

   private static String gJ() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s7uej2uzc4zes","PSvLZHt8fActoSSRZ9oDn/y6sEzagrUQJxSfNReEg+8=",5314669763312614727,-3967801243285208353,2020040878822256391,4937366313840163674>()) {
            case 1509219911:
               if (var0.getConnection() != null) {
                  PlayerInfo var1 = var0.getConnection().getPlayerInfo(var0.player.getUUID());
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s92fga4y72u0p","UPWKFXvsk7sDNdgjElmzMx/naL+0Ec+DvmruJiRCZis=",1944386068475203795,6596259323695003463,-7110379412166929449,5096778771244504997>()) {
                        case -74551256:
                           String var10000 = (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
                           switch ((int)com.yiyiaddon.m.b.a<"s2vu984f2orbdx","xumcppQaiZ+itBEXA8M/XN4xCkeoXfylDi21C5YM1d0=",-1566011539954025209,-7394279417483218933,4229200283662795928,-2031148903773132286>()) {
                              case 301881998:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var2 = var1.getLatency() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s103ayq36e26h1","1D5K7PYzH92fFaWBLGGkgHQHcHk7B/J6aYodIXErJ+M=",-3007041453834552075,-8276036215866152139,7734117403083796401,-2845489458892954659>()) {
                        case -1679117119:
                           return var2;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ry0cz0x88itz","uuPkJanXxJJIDz70Mfp/JKmv8nxY+r6dj7DENRCEh1A=",2443881788399371991,-604284753170093625,1709138897280115062,-2500782746048910418>()) {
                     case 2003225703:
                        return (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
      }
   }

   private static String gK() {
      long var0 = Math.max(0L, (System.currentTimeMillis() - bh) / 1000L);
      long var2 = var0 / 3600L;
      long var4 = var0 % 3600L / 60L;
      if (var2 > 0L) {
         switch ((int)com.yiyiaddon.m.b.a<"s2d2nsdh8i2h0i","vwfldLofeXfnX7iKHz+FN5P6a70k1o39tckI1cCvaKs=",4145808667987870811,4969404295505477260,8424301896086496221,8256242371926745537>()) {
            case 1090287825:
               return var2
                  + com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s2q43wah3ksow7","hG3lBUCC5hKM++Flfrpn6wqW+mtf16i5ggxzcOT1Hvnp2OXF",1912032512612874903,-7772641508555463111,-5958427029081650377,6435971546154472735>(),
                     (String)com.yiyiaddon.m.b.a<"s2rlwwnd9d73ra","l/N26cI041no70nf8xYA4jRzWVbH7pHEmLae5CgQAixZQw==",-4363713666105840428,-2433622327683582086,-5041888044855511287,-3540228331420214062>()
                  )
                  + var4
                  + com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s27l5eivl4k56s","7qwGLFt9FN2Vtpgwedc9cdQFG9UulJB4rsqKv2sVVdE=",6230473230371415832,-2767251106743722319,-2769678661829568079,6123137259019241934>(),
                     (String)com.yiyiaddon.m.b.a<"scmk7t64yx4ov","Cil0DWIs8yepFJozKgC5qLEpsXDV2or8SON/Py5oQzTF36NQ",1206175495423751795,2189154494192608225,6004811521870634057,31775860375333394>()
                  );
            default:
               throw null;
         }
      } else if (var4 > 0L) {
         switch ((int)com.yiyiaddon.m.b.a<"s1m9aabr14rhc3","UDpmdG7C9JZYSN4lvy1Y1zTk1RzK5JEucbOA/V0bupo=",3616064153988371669,8245969452150746840,-3953139422730227163,-2301821308649336678>()) {
            case 1964045961:
               return var4
                  + com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s1ojmveqttmimb","qUnylAOn88WWBgP223taNdRBdcg//UIykWG6XXBJ4wXYNw==",-2323336392665985850,7098116727745681044,-80269388115655398,8757929644387986464>(),
                     (String)com.yiyiaddon.m.b.a<"s1tlflpyou27x6","zfu0eJqNPNHOxaPBdT25s0tbZikdtHJOYwSTRx/p4JCWTE1Saq4=",-8133654505122955613,6209076727036953996,5048786899243682725,-4812018955832971570>()
                  )
                  + var0 % 60L
                  + com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s30ihzgcykzaej","Si7ua0NBQH1QBCo4mmQ0trFYtGus7vCX34tND79b9Jg=",-7902903357626727951,4864257701155109369,-943756717200949446,6409789180513227730>(),
                     (String)com.yiyiaddon.m.b.a<"s21uwwlubblg4c","dJdkBj+nEPojcj+1gvKbi5nlVwa4sxn4Arjnvnk5kxA=",-336142809482874212,-982578801609980757,6859398792830429516,2415755413350426346>()
                  );
            default:
               throw null;
         }
      } else {
         return var0
            + com.yiyiaddon.l.a.w(
               (String)com.yiyiaddon.m.b.a<"s30ihzgcykzaej","Si7ua0NBQH1QBCo4mmQ0trFYtGus7vCX34tND79b9Jg=",-7902903357626727951,4864257701155109369,-943756717200949446,6409789180513227730>(),
               (String)com.yiyiaddon.m.b.a<"s21uwwlubblg4c","dJdkBj+nEPojcj+1gvKbi5nlVwa4sxn4Arjnvnk5kxA=",-336142809482874212,-982578801609980757,6859398792830429516,2415755413350426346>()
            );
      }
   }

   private static boolean aT(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"spnb17fiti4w","KPG57LlfBi6Dh91HZHS/GCTbA0e9TZfpPdQOrb50IBo=",-8363680159820708636,-5943443066514335957,3639384707154107618,-430849866008718754>()) {
            case 344447433:
               if (!var0.isBlank()) {
                  if (!dr.contains(var0)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s38vjyulil108m","JNZTApkwnyaywFdd5VkRYBO3NXKeo49QbW/G9CQlx0Y=",7040727441964784213,-7811710755329656844,-8166739364212722184,-1505489116703054681>()) {
                        case -241550:
                           switch ((int)com.yiyiaddon.m.b.a<"s3m00r46zsxfgs","BYIKRh9gLuZryV906qcpghR9W44iVVyaYYpdImsVR1A=",8165396791918708232,9085290869176948296,2557040718348119685,-1666693081028288622>()) {
                              case 776457891:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s26jash6l1lf01","x8NQYmF0trA8nxXXuoQwVVZErlXAktkC+WUaUZ0ecIU=",4043602923601625252,6756526499860758856,437112629819251842,-4379499811084148374>()) {
                        case 1244121909:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2vv609za0b842","/J+Vu3JTelJOnY0XtCscgA0rTNqjWeXl7VGBP2xtl0s=",-850136199951851123,6180449587046466752,-1212341004997665485,-8528858947143350287>()) {
                     case -1029523996:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   private static String cm(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3mkkt7f3n48uf","4JVMhkpAWUi4KXZA8CtV3mFPZgYf4TePMHjyMxr8Gkw=",8019197556992330168,-7104438285135863651,-7571260768143782087,3749375734018610444>()) {
            case -27843155:
               if (!var0.isBlank()) {
                  String var1 = var0.trim().toUpperCase(Locale.ROOT);
                  return bh.getOrDefault(var1, var1);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s5lplmkrooxgs","22CS+iaRNh6hYrrgNaEhX2S+4xfIBURhn9t2/4CyukQ=",-7367939684183226453,3993602861504391332,3753816945370157553,-4786536673817423856>()) {
                     case 436962854:
                        return (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
      }
   }

   private static String gL() {
      long var0 = com.yiyiaddon.k.e.A();
      if (var0 <= 0L) {
         switch ((int)com.yiyiaddon.m.b.a<"s2j3li524vxz4f","NfrwPBD9rf3Z5FkmFdyw2t7IoiEh4DQKM+7E9AqJewo=",-3499386297850614438,-7096284496107804094,2517079643180863486,-8282991613294450996>()) {
            case 674424545:
               return (String)com.yiyiaddon.m.b.a<"sziqqxv86w6ow","b33y5IP9LkXOqW4XFW+9HOQGmjGIRnypZtaTMCrGaRA=",-9102693839628558065,-6519131305817453180,7271681344560034312,8940793856931360278>();
            default:
               throw null;
         }
      } else {
         return c.format(Instant.ofEpochMilli(var0).atZone(ZoneId.systemDefault()));
      }
   }

   private record a(com.yiyiaddon.h.d a, String FC) {
      public com.yiyiaddon.h.d b() {
         return this.a;
      }

      public String B() {
         return this.FC;
      }
   }

   private record b(String FD, String FE) {
      public String gM() {
         return this.FD;
      }

      public String B() {
         return this.FE;
      }
   }
}
