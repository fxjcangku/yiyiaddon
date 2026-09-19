package com.yiyiaddon.e.c.e;

import com.yiyiaddon.e.c.d.g;
import com.yiyiaddon.l.g.a.d;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult.Fail;
import net.minecraft.world.InteractionResult.Pass;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public final class a {
   private static final String cb = (String)com.yiyiaddon.m.b.a<"s2weoxtrgn2ikv","FQ+IHqAVGKcZLZa0N1BAr8zmF4im6y1YjvpwcBofYzfuvjQM+qzQEXGAWHg7oyZ1MqfLlwnk02slXzj7UcCMElvZ1Ew=",-4992328905000464088,8363446011787822854,-5839371048164976810,-5447902829745226574>();
   private static final float I = 1.5F;
   private static final d i = new d(65380, 200);
   private static final d j = new d(51455, 40);
   private static final d k = new d(51455, 160);
   private static final d l = new d(16762880, 200);
   private static final float J = 10.0F;
   private static a b;
   private static boolean K;
   private final Minecraft l = Minecraft.getInstance();
   private final com.yiyiaddon.e.c.a a;
   private boolean L;
   private g a;
   private BlockPos f;

   public a(com.yiyiaddon.e.c.a var1) {
      this.a = var1;
      b = this;
      if (!K) {
         K = true;
         AttackBlockCallback.EVENT
            .register(
               (var0, var1x, var2, var3, var4) -> {
                  a var5 = b;
                  if (var5 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2xj541rtqndsm","GmdWqV3K/+eK2BaZd33JZu40W+vSiPeNocgIB7IInJY=",352605876087198612,3550304039994017282,-4373055947018559734,2994921570458150960>()) {
                        case 1741497153:
                           if (var5.a(var0, var2)) {
                              var5.d(var3);
                              return InteractionResult.FAIL;
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"s1gh700czxu8h7","kYWe3STlpLnSN0IWPpBMDmbIVvUG83Q8dQhI6cGx6YM=",-6313469269092822026,8952607787683231400,6513520549814447115,-8120600762550391858>()) {
                                 case -351035741:
                                    return InteractionResult.PASS;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     return InteractionResult.PASS;
                  }
               }
            );
         UseBlockCallback.EVENT
            .register(
               (var0, var1x, var2, var3) -> {
                  a var4 = b;
                  if (var4 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s36fv83zeif4ms","ZIeTwyAlYKw7h6PvBHgfhK4utYGLCdyMstukGjLZBfg=",6748730719358242662,-6848688229145567372,7820689590443382461,3090553322538142685>()) {
                        case -1890195496:
                           if (var4.a(var0, var2)) {
                              var4.a(var3);
                              return InteractionResult.FAIL;
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"s37kvi7irgxmgi","9mpFbvGIqzG9DKkGiATSL3mzCeFZYf19oi+SmcKo8oA=",3219492747977696756,6866373233642524852,-3635877872665358284,-9143339985741662745>()) {
                                 case -1628534651:
                                    return InteractionResult.PASS;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     return InteractionResult.PASS;
                  }
               }
            );
         UseItemCallback.EVENT
            .register(
               (var0, var1x, var2) -> {
                  a var3 = b;
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ha1pnadihayb","ZxEvEZ0bJuRvciGuiP/BKQtGMlwtkE+NXKNa9gUUONQ=",2771891390880811520,2324997422457189199,3831503606070727456,-7921029291118383452>()) {
                        case 332366163:
                           if (var3.a(var0, var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s16a8y9bb9q2fx","Q37YiG/kO6R4prZdR9rutA8VBGaNSz2uj79zXptXgtA=",355934870285784850,9062655655834465674,-5766853419060821227,7080189473938079889>()) {
                                 case -1382485693:
                                    Fail var4 = InteractionResult.FAIL;
                                    switch ((int)com.yiyiaddon.m.b.a<"suuiyzi07a0nh","yYMYndkXoLeKDyc6D6mp5yO1s5QMNLb5NW7WXWd83GU=",-5628345462735696802,-5133278531346745794,-4054239630798519941,-8130568492915093086>()) {
                                       case 1841500644:
                                          return var4;
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

                  Pass var10000 = InteractionResult.PASS;
                  switch ((int)com.yiyiaddon.m.b.a<"s2nnadx85hkqsu","W7+gSN7MgWcC7sefXztKn1Cytsmy79i4GIxzbGrxeT8=",5223934350247841810,1312345935139690434,2674210273935577755,2168281872246784050>()) {
                     case 98749388:
                        return var10000;
                     default:
                        throw null;
                  }
               }
            );
         AttackEntityCallback.EVENT
            .register(
               (var0, var1x, var2, var3, var4) -> {
                  a var5 = b;
                  if (var5 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3q1jq5wl01r30","JzLguTgruNnq49fmH90thEFPg/dOqmP2Xaeg2ZQ1Nms=",951171856613547761,-4592554607018518150,6796967089983053936,7854629619513123619>()) {
                        case -1055212073:
                           if (var5.a(var0, var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s29axftj768qsu","wPMKx5cdFsS5rttBmJtEiOBtY3vbwPRqe/emrO9tR4Q=",-2100325630028913385,254628414812534302,-312330013583892562,-8810920938891130767>()) {
                                 case -431302936:
                                    Fail var6 = InteractionResult.FAIL;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1vginff5cpwi7","rj1cIxR8TOzxpmjeAELbxu2BLzo0mxYEuLaLEO/0YUo=",-3875182727662039542,-6021859763730205867,7306471557521062262,-8912629543498007835>()) {
                                       case 40449939:
                                          return var6;
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

                  Pass var10000 = InteractionResult.PASS;
                  switch ((int)com.yiyiaddon.m.b.a<"s3qd66eevk0b06","aZ6/vmT9138YBjOGkPskWsokYfABFoZr/TqkyhzuF0k=",8510302975745947831,261627803067289300,-1838943930499727897,2963566745475884498>()) {
                     case -728680612:
                        return var10000;
                     default:
                        throw null;
                  }
               }
            );
         ClientPreAttackCallback.EVENT
            .register(
               (var0, var1x, var2) -> {
                  a var3 = b;
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1rulcn3j812rf","AM463TYVe8EeV2GAyd3KIb9KqpOS7zjbh+TIV7jGZZk=",2796458062020478732,6390075254120784354,3614947155278265741,-8966282654762486110>()) {
                        case 1637698697:
                           if (var3.a(var1x, InteractionHand.MAIN_HAND)) {
                              BlockPos var4 = var3.b();
                              if (var4 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s14w3hhgpaz9em","wqQq4L79WMsb9xmTDZqp94ErAwL9Wiuz96ypKwbFtqM=",2497564051790934792,-2965544213778271424,6168186848562579655,6920828475764608986>()) {
                                    case -2117638747:
                                       var3.d(var4);
                                       switch ((int)com.yiyiaddon.m.b.a<"s1t7m5o0za0194","hWv5aD4wX/00blBrsEiomH2214/zqbHdCSIYlgLPDGs=",-8923312858458720343,863166306910349458,3375417521491582889,6268462291179040980>()) {
                                          case -1427429208:
                                             return true;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return true;
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"s34xdg7j9qh2ru","zztBO9Z9UuJZd/wBxKxVvh360jPSwWji5bkgxbL7dEI=",3648397909276873362,-6234178423395975308,3702277595558597861,-2709029065755432882>()) {
                                 case 1467266259:
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
            );
      }
   }

   public boolean isActive() {
      return this.L;
   }

   public g a() {
      if (this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"s2j72mxtx0ocjh","zfv/nHH6C4GqwSw6efrnwyQgkjwfFm3dTTwFmi3vIDE=",5127783285018097621,391259658863171885,-904866825755282881,-2710554290949896506>()) {
            case 2082101733:
               g var10000 = this.a;
               switch ((int)com.yiyiaddon.m.b.a<"s21nbe8cjg7oro","U/b7k6OwiURZhHfhw2khh9xDWSt9UlVupFL7DD4FHLE=",-6707248084752696972,5595605907601902613,7873659893443222957,-173423690069958025>()) {
                  case 1952198455:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2og6oiulnmd6o","NIlZCfyCPjBJuemIXjTMCSfAGvqsW+lwg1EKTsTe9yw=",-1339854427700385870,8827927628494340025,2903267632549574545,-5208791621268745697>()) {
            case 1431121904:
               return null;
            default:
               throw null;
         }
      }
   }

   public String b(g var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3pwa376r1g7f7","kyE9aMeFPzyCPxdR3nPdNcAEQDBlZshqciac+vFQrHw=",1678330561548510580,-4695566916835233418,-4860285121001982076,1879416757219521903>()) {
            case 647488543:
               return (String)com.yiyiaddon.m.b.a<"s3oac03f2hekup","D0BpFHkiYWrIYskTEODtW/nJfKQ0dtvHXIOUKR1053+MlsljE0ten6g3",6208815658366430924,6683413040419937525,-4324949268665514842,8994311316320318673>();
            default:
               throw null;
         }
      } else if (this.a.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ydvboj718yi","qq5yXvbMjgea9n99GFctpHNkfk3dnXdMEZQ+GdWTa4g=",-8796609487460284499,-352640923803900994,2979982024185775231,-1792606071566025735>()) {
            case 1175443288:
               return (String)com.yiyiaddon.m.b.a<"s2bspf3aze7noj","aF8R3quBQ7QwsqJjlmqzisbJ0+ZU1grAdUugCtHc8YCx6w/KI2XDK4F4n96w/3tr61tl+QSFsOd68/yCzglj2w==",3264562025276388567,-6865916416779538354,-4578860133926003931,-7616528288069089259>();
            default:
               throw null;
         }
      } else if (this.l.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s24doo25gwmmof","x7XGVlK946fONjb64absMx2Ge/qyfxwCuRviPds0tzA=",-5169876179445752797,7485681969308732460,-5500113896255295753,-7447193660257243892>()) {
            case -2011506549:
               if (this.l.player != null) {
                  if (this.a.a(var1) != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3bro2fgws7c26","Wcc0r9EYHvHmUV/QJ2oC3BTGziu1ZghCQx/ShdxXpfc=",-6368398909201284185,6953775678817852229,-4317811892257689403,-7734895338938318589>()) {
                        case -1307308466:
                           return var1.af() + "";
                        default:
                           throw null;
                     }
                  } else {
                     label108: {
                        if (var1 != g.START) {
                           label74:
                           switch ((int)com.yiyiaddon.m.b.a<"susz39646596k","K/2rwasquVU3fGi6AA8CPKtikKpDPncygmglrMebB9A=",-4924471903125773750,3814935675322295056,-5605527767714637391,-647331646351630714>()) {
                              case -975800262:
                                 if (var1 != g.END) {
                                    break label108;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s11dbxvj8yjmwy","7/jPLuFdCLPGH+cauRH5OAEyKcxCjR3UCnrRVl1KuYY=",337360716870821553,-2398510114138324824,6365620085075413119,-1495305964695232688>()) {
                                    case 1083810777:
                                       break label74;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        g var10000;
                        if (var1 == g.START) {
                           label67:
                           switch ((int)com.yiyiaddon.m.b.a<"s1i27omrqhkpha","p2Lr+XqPWGIqqTxDxgU9cQpSkZmZp/Cni1zlKFtgIlQ=",-1936002773019301625,904757351068258587,-5390957352698362061,8784504644374246786>()) {
                              case -2092381560:
                                 var10000 = g.END;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3lf44q915sz","c7DRPVIL/BYtA62eQqLgskj1sMb27JMIUeq/6pU6fTM=",9017623453006096526,9089021664677882959,-7944608932588529168,4923092898865358228>()) {
                                    case 1190239126:
                                       break label67;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10000 = g.START;
                           switch ((int)com.yiyiaddon.m.b.a<"s1hs262fqw10d","9CvjZ5cDksxvm/DyCn9nWS9QrnKJNJ3xLVv+ozkH1AY=",-6484055868685042067,-1001418217444762597,233872113353169343,-7959365422694796483>()) {
                              case 1951812985:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        g var2 = var10000;
                        if (this.a.a(var2) != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2nwkmrx0wmodn","cQKYa44fPOE5gjgpz8UucooOpEfX+9NDrTQkWBBa/Ro=",6435269249303517959,144509671690873889,-2435257222663958708,-6933910898152239574>()) {
                              case 735943667:
                                 return var2.af() + "";
                              default:
                                 throw null;
                           }
                        }
                     }

                     this.L = true;
                     this.a = var1;
                     this.f = null;
                     com.yiyiaddon.l.g.a.l.a(
                        (String)com.yiyiaddon.m.b.a<"s2weoxtrgn2ikv","FQ+IHqAVGKcZLZa0N1BAr8zmF4im6y1YjvpwcBofYzfuvjQM+qzQEXGAWHg7oyZ1MqfLlwnk02slXzj7UcCMElvZ1Ew=",-4992328905000464088,8363446011787822854,-5839371048164976810,-5447902829745226574>(),
                        this::render
                     );
                     if (var1 != g.START) {
                        label62:
                        switch ((int)com.yiyiaddon.m.b.a<"s12fuedh9tt7ib","qJe7Ub6pSIRQR4R3AVFN3xz+Q4DuD1uTSDEHmsTuvBk=",-7586264167681082754,3360243540534438611,1014693052047147114,-2623644343121896302>()) {
                           case 1268511144:
                              if (var1 != g.END) {
                                 com.yiyiaddon.d.d.a(
                                       (String)com.yiyiaddon.m.b.a<"sdj6vw1ldppht","ucSZYNvUKS+jiPTCPxn479UXPLBda6QDFnln/6POROM6VPDA",1412308265105651441,-164761888105255434,7534787039347284061,3478138056022017281>(),
                                       var1.af() + ""
                                    )
                                    .b(
                                       (String)com.yiyiaddon.m.b.a<"s372qitld24ff","rIWZUckKDHdYVRhy8kSMwWxVrdLCjE8X+0N0N91mVGeidw==",-4090096863099107958,4313155559677433837,4378212757694465501,-2631943657490096783>(),
                                       (String)com.yiyiaddon.m.b.a<"siqs3r0fx2km7","wg2PtNmMI+2bN5EqqdQyb8ZyDEIgrL334MvCQceUafpeEzh3YzHF2eTH7okM5p6Y3nDqcDdOCQnsaeeu+jCLSl+9oYp8Ow==",6334266996085429633,-2574718200472033482,7272328650968396613,-7140974111711855989>()
                                    )
                                    .b(
                                       (String)com.yiyiaddon.m.b.a<"s2f21n51kzx9ip","19o2GLlhhrfNsE9PwQTD9rQYuMN3GNnV5mqAVbpQssg=",3737465464064613940,6302192519359678153,2886693106243168722,-3882067839527138015>(),
                                       (String)com.yiyiaddon.m.b.a<"s32duq048bjwx8","RT1Gc8FiWmPhkDWlD3+F6XGWzbCXSjE17Cgzf1d0ldWUPgfLCis=",-2927696817475224963,4023084240224458286,-2447055761632741281,933484679360444361>()
                                    )
                                    .a(
                                       com.yiyiaddon.d.d.a.SUCCESS,
                                       (String)com.yiyiaddon.m.b.a<"s3bqn8v43ww93v","5+CP32HnXoB4pzBOpkcM2aNdGHw5bTy+m+X02x6kZ2n4Xs6rHcNUXw==",8646927526680227460,-7863663141750883691,6076477537919643663,3595904920018766360>()
                                    )
                                    .g();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3gam1epecvh82","MWj1JzPRYnJlSL9ydv4d0E+mAKxHFi+BFLzN5QAFH0w=",-6891077332421208765,-180627139272094992,-5334593445283700324,5317244009138834624>()) {
                                    case 138266377:
                                       return null;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1c3upxb1b5ptx","GOBCLs0H/Vul8TeNzMZogch6a4+Da6GfUSNXRSJnSIc=",6762173491747623555,-962143223538873424,7094224930441107983,-2459658593740731169>()) {
                                 case -1429432674:
                                    break label62;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     com.yiyiaddon.d.d.a(
                           (String)com.yiyiaddon.m.b.a<"sdj6vw1ldppht","ucSZYNvUKS+jiPTCPxn479UXPLBda6QDFnln/6POROM6VPDA",1412308265105651441,-164761888105255434,7534787039347284061,3478138056022017281>(),
                           (String)com.yiyiaddon.m.b.a<"s18tu0rkwfvr9o","yAePDGotZMvnTfbiUJyD8zdSsPbVWri/93+fDdTykQk6b9Cm3ITMLg==",3386849962060691686,3884736116072297883,7808521102708545814,-3396869399367014778>()
                        )
                        .b(
                           (String)com.yiyiaddon.m.b.a<"s372qitld24ff","rIWZUckKDHdYVRhy8kSMwWxVrdLCjE8X+0N0N91mVGeidw==",-4090096863099107958,4313155559677433837,4378212757694465501,-2631943657490096783>(),
                           (String)com.yiyiaddon.m.b.a<"sobvnczjrxmp6","IvYOB/UcR2cNe7HEqIJuCTAHZF14zHFXGEWqSngfwxzKs1vW8/YUy9jMNm8GlsoWMvTFgTputhK1MA==",-5535503028158399669,4461569477829718666,-5281510547321543180,4901164581204717549>()
                        )
                        .b(
                           (String)com.yiyiaddon.m.b.a<"s2f21n51kzx9ip","19o2GLlhhrfNsE9PwQTD9rQYuMN3GNnV5mqAVbpQssg=",3737465464064613940,6302192519359678153,2886693106243168722,-3882067839527138015>(),
                           (String)com.yiyiaddon.m.b.a<"s2q9gzntpszjc6","3YO+z0nWLfl6K3J9CDQzWJ451ch7VkuYFkEnoFLjb9/lRcmbAzWbaopAQcGpaf88lgzF3nSV",-7502012522462142984,-7879089695032258047,-4583520822892760327,3980424359964750512>()
                        )
                        .a(
                           com.yiyiaddon.d.d.a.SUCCESS,
                           (String)com.yiyiaddon.m.b.a<"s1lm0ee860muc3","n5SD5azoGxXc84Mu30iD6gw8HjVWSKjHOUNNHfAcg8pRfjqS+Fo=",7560946577728794103,4016718503019130911,-3161226369716964154,-8707988032747025627>()
                        )
                        .g();
                     switch ((int)com.yiyiaddon.m.b.a<"s3gw8yv2zrsvzu","uuDSKfhtqh9wkR0vnNOh6Og3eKkrowqEIQOx+qyOoNI=",-7254210590613404912,54846272144279678,-3231329014404971563,-6902844180425701852>()) {
                        case 1651108903:
                           return null;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3iuwb0gray3a6","KH4PfYhq3r46p1SLOe48d5gZbJe17lbecgXJ3gBJ0vY=",-4340326810164365574,6717663035860110867,7583214149240318270,7533886287031388164>()) {
                     case -437455198:
                        return (String)com.yiyiaddon.m.b.a<"s6dpr2zt0vcdr","8/3O0JujZQhG31xYt6CUm+NrRD+WCDUoMweoMpQ9npiwqK+c68MCSk0ymasxcnDStyEKeCIDGsCJT19o",1547126567398494977,-629986749347319888,2793962176124028050,-8806498501441190068>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s6dpr2zt0vcdr","8/3O0JujZQhG31xYt6CUm+NrRD+WCDUoMweoMpQ9npiwqK+c68MCSk0ymasxcnDStyEKeCIDGsCJT19o",1547126567398494977,-629986749347319888,2793962176124028050,-8806498501441190068>();
      }
   }

   public void d(boolean var1) {
      boolean var2 = this.L;
      this.L = false;
      this.a = null;
      this.f = null;
      com.yiyiaddon.l.g.a.l.l(
         (String)com.yiyiaddon.m.b.a<"s2weoxtrgn2ikv","FQ+IHqAVGKcZLZa0N1BAr8zmF4im6y1YjvpwcBofYzfuvjQM+qzQEXGAWHg7oyZ1MqfLlwnk02slXzj7UcCMElvZ1Ew=",-4992328905000464088,8363446011787822854,-5839371048164976810,-5447902829745226574>()
      );
      if (!var1) {
         switch ((int)com.yiyiaddon.m.b.a<"smr9nk8cgwydy","ONaw6P5IZxDxImkhOkUGxCye3yIl3QaQ4YGLwExKv0s=",-6890006591457113103,-4502866872297778280,5206438638852360944,5219989300598639152>()) {
            case 889280456:
               if (var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"sus9mpkrhlfqu","HrI9MOsiJKIPQdZccSY412GOCiUG/3PM/LUiZ3et6q8=",-4091521084154909147,-1352291792707110580,-6591894435018621900,4688106134595602820>()) {
                     case 2032410687:
                        com.yiyiaddon.d.d.a(
                              (String)com.yiyiaddon.m.b.a<"sdj6vw1ldppht","ucSZYNvUKS+jiPTCPxn479UXPLBda6QDFnln/6POROM6VPDA",1412308265105651441,-164761888105255434,7534787039347284061,3478138056022017281>(),
                              (String)com.yiyiaddon.m.b.a<"s373tnpetbmr1y","dE5aHjp6OOBdGMI5EuTgTBvsooDmnwjlJbk0Nc2YKi43+ax8PBh6Q2QT",3081703883018008974,-6940165500187511074,-625302911571548342,3302748627829186156>()
                           )
                           .a(
                              com.yiyiaddon.d.d.a.SUCCESS,
                              (String)com.yiyiaddon.m.b.a<"s1qjjua8eseq8n","XJ1H7JTdoruZk66xI+/sOiYCZikhHSU3mziuUjS4YjGucKpeJ+w4P4vbVjbWyc0WswDwSej7N4fUsg==",984925695451416409,1037335048531086682,1318672714284882665,115623969654152724>()
                           )
                           .g();
                        switch ((int)com.yiyiaddon.m.b.a<"s1th9lua0czfp3","m3VC2UDMSGTl1pZIwEuSEsJ/tNW78Byh2q5m37WWZBk=",-592746183956023431,6502514453487286738,4761443405838693619,-9182451883683035596>()) {
                           case 116369258:
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
   }

   public static void e(boolean var0) {
      a var1 = b;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1522oil2z9wph","w5txFj+D55qtox5atb3fn7bXtqMLExiD9isvI8X1VYQ=",3530617011381958552,-2535242626278444328,-298937163307529127,3490501731926295029>()) {
            case -937709770:
               if (var1.isActive()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3nshb99680qdh","SEgKaYwl8ju9L3YEBtJSIRQ24eunak7EkVznoWdERwI=",-6017125401324203571,947009772406615752,-6842048490871286514,-563234487275216376>()) {
                     case -103121684:
                        var1.d(var0);
                        switch ((int)com.yiyiaddon.m.b.a<"spsaormss8m78","eyWZ0pzY8g9Va+A41rufGkLuUOXBbud9/0StIMHeYfs=",-3112616219282385002,8685872752433551258,8530946055461838516,4242987519234316115>()) {
                           case 1115747546:
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
   }

   private void d(BlockPos var1) {
      if (this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"sjmtop2pf9i3m","lGfIxYdGlbLIKibxCuPftnfYIwf6UDNpkBNJ8blFnUM=",1128535486835809992,2175117179226055247,1812654209232395529,6065368287091950790>()) {
            case -622372979:
               if (var1 != null) {
                  if (this.a != g.START) {
                     label31:
                     switch ((int)com.yiyiaddon.m.b.a<"s218t4egxpe828","xvcQERH77lMxCsHRmiSVyYwRIRNCnziRPNektR1Ve3E=",-4356155403268125433,6094099487787250938,-6718072295660131786,5509088503385057388>()) {
                        case -1291257945:
                           if (this.a != g.END) {
                              this.f(var1.immutable());
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s33w89vhuu20l6","5azt8Ao3fFDCIqnJtESqY8RUcq+amBNV0MJNVHyUDPc=",4873838728629250140,8839654140844216462,5206778646068976275,5198303808252966956>()) {
                              case -1629121595:
                                 break label31;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  BlockPos var2 = var1.immutable();
                  if (var2.equals(this.f)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s30rfg7nj9yj4p","ktXaQ+BOz5auBidjHlpDtSTA3vmsMOyFQhgkDE5Ypog=",-8982981929322622377,3062295182169192421,928262199557081480,-5451430541448115417>()) {
                        case -713754747:
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.f = var2;
                  com.yiyiaddon.d.d.a(
                        (String)com.yiyiaddon.m.b.a<"sdj6vw1ldppht","ucSZYNvUKS+jiPTCPxn479UXPLBda6QDFnln/6POROM6VPDA",1412308265105651441,-164761888105255434,7534787039347284061,3478138056022017281>(),
                        (String)com.yiyiaddon.m.b.a<"s2nsu9bh143v1f","YUIYUfbyUYyVQjLkYl4xufO3ObnwNg/TdeaIrFByIlNprT+7i3uMyXUu",662175729847188412,4828299615164702940,662438463569449454,-5041471006346406838>()
                     )
                     .a(var2.getX(), var2.getY(), var2.getZ())
                     .a(
                        com.yiyiaddon.d.d.a.SUCCESS,
                        (String)com.yiyiaddon.m.b.a<"s1dtwpe6njo8gx","VGYwy/Qhm8zwEmQ0MCXybBPOdTrOM88GRy2ARm/DBDBBnorRv8JnHnBw",326892868238572495,-856628548488057733,8143278221652961565,4229880672095560309>()
                     )
                     .g();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"shk9yszidhamv","iqX64LElWB+b2/vcKsl9bDeNSD91JP7MlEwmfNAVOQI=",6387320431907701502,-771356990296684611,6206783428855811723,1791399571273298297>()) {
                     case -747724527:
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

   private void a(BlockHitResult var1) {
      if (!this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tp2os1wdgl7b","YRvArE7B+kPoDy8HeI3jhRui+d+Ryi/W7csdPw2UeBQ=",3139178263243797947,-5877216746340104892,8817752030672914256,3590120304242183683>()) {
            case -857094367:
               return;
            default:
               throw null;
         }
      } else {
         if (this.a != g.START) {
            label29:
            switch ((int)com.yiyiaddon.m.b.a<"s3enojw5g7gl74","9H8HDdfVArt5IRRHSJTcasaeaYnrS7fJue9l6FeXkLw=",1632026717736770676,1809449821723136950,-7073099864024437245,6855041767382343769>()) {
               case -391754132:
                  if (this.a != g.END) {
                     this.d(false);
                     return;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3ip52el21a15j","PO5tWSoVAED8ZLltqtHxwlnr5rbI2gWjJo9M3ccj5sg=",299266991171657723,-785981517657453354,2752918568589090309,7250040844138370126>()) {
                     case 251930376:
                        break label29;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.f == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3elujk2lv4tpq","wG7oZwBY/OzL9aNRBXB2TreGXa0IdIdjG+lYvZulKE8=",7600162014599271829,5671682643457524144,-4048469741376133603,-1619371083616827499>()) {
               case -1821403735:
                  this.d(false);
                  return;
               default:
                  throw null;
            }
         } else {
            this.e(var1.getBlockPos().immutable());
         }
      }
   }

   private void e(BlockPos var1) {
      com.yiyiaddon.e.c.d.b var2 = com.yiyiaddon.e.c.d.b.a(this.f);
      com.yiyiaddon.e.c.d.b var3 = com.yiyiaddon.e.c.d.b.a(var1);
      if (var2 != null) {
         label17:
         switch ((int)com.yiyiaddon.m.b.a<"s29augag4cbyjn","P37+u9iSFLNO6Wv7JGRR0Bo8Oh7lhu4uewvbULks2AA=",-770669410334503988,261470482313346790,-3298359817079848706,-9152467599510741515>()) {
            case -1942715504:
               if (var3 != null) {
                  this.a.a(g.START, var2);
                  this.a.a(g.END, var3);
                  com.yiyiaddon.e.c.d.b var4 = this.a.a(g.START);
                  com.yiyiaddon.e.c.d.b var5 = this.a.a(g.END);
                  int var6 = Math.abs(var5.a().getX() - var4.a().getX()) + 1;
                  int var7 = Math.abs(var5.a().getZ() - var4.a().getZ()) + 1;
                  com.yiyiaddon.d.d.a(
                        (String)com.yiyiaddon.m.b.a<"sdj6vw1ldppht","ucSZYNvUKS+jiPTCPxn479UXPLBda6QDFnln/6POROM6VPDA",1412308265105651441,-164761888105255434,7534787039347284061,3478138056022017281>(),
                        (String)com.yiyiaddon.m.b.a<"s3tk7p5jfu2d71","/Cv9oi5SZuCnxpsZyzHSuzW62LhBHwYiEi0IGcZLqVzt7WC2wV5jSj9i",-1325327728402669863,-2229506118069658760,-5931450611196175060,-2263852570026698368>()
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"sy4eby3mvg1et","WlTH6dQerXZQ26iSoHUn9aCOTvdutmApneAjDRmcDPOah5+1EXA=",5842855058170765947,-2149109957011723185,944989854620886352,-4401484192857762570>(),
                        var4.q(
                           (String)com.yiyiaddon.m.b.a<"s2ylutg58q386p","yRDWzJGq0Xaz+E3MWZCZeJMQV1fDlmMyC54emQdrQxg=",3365323970292739778,-3536802804957423284,-4417257997857262380,3882220984904702249>()
                        )
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s2fdo8nf4bpvwm","70Ls+gXCdaCO51FivoC5e8UhNPaFk6HvOHt/FaRE4SQNhxeRbH4=",-175362129474339095,8273703544403618876,7598886886138382454,-6938676442838032917>(),
                        var5.q(
                           (String)com.yiyiaddon.m.b.a<"s8b8i55uq0u53","0VWfqZJqOxhQGTZ0XrvAAttNhf/mBFZKHiUgS9gzMoY=",-4361600503807827184,8057418436236765033,-9054386156963259299,-2931519572951752062>()
                        )
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s2e71aqgxuus3w","dDg/cpqd9poMnGFW1KMjx3daa3guh7eO+grn6AIFnCs=",7304841409667879999,896710677390676543,-5624441540362638916,5726957817231916177>(),
                        "" + var6 + var7
                     )
                     .a(
                        com.yiyiaddon.d.d.a.SUCCESS,
                        (String)com.yiyiaddon.m.b.a<"s3jp3jjxnfy034","wKL711AhjE2akW3oTRNaXbP3F6YY/jA008hbduhmNG7DBw==",8825686037989672555,-5755051258471916664,-3603926824538707953,-806275200224084964>()
                     )
                     .g();
                  this.d(true);
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3mrk1f6r9ufva","cnpMTBiQi9yV2hLOjeLpkuBgZaRucqixnPjyPcVTQfs=",8048061432783513963,-5989851012799082047,-1547189667752563327,8434608289660433685>()) {
                  case 1208097358:
                     break label17;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.A(
         (String)com.yiyiaddon.m.b.a<"s2pc79jv7bxcc2","ZPFsLOHmcJ/BIKIb7E97b5VX26rQJfYIH/uhOYL4YuzFikK8Q/8aW04SMLWcUeFs",7092457923220790793,-1893871950641440492,499184249252713242,-2152208734041305547>()
      );
   }

   private void f(BlockPos var1) {
      if (this.l.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s731wfjuke3kk","SFWN1VtU3cX4T3OFj8I9+MNgrNG/LKBRWRMCGfbXiUc=",5187115385704460172,-2142431955295971096,-7910719372076983081,2424441159178867199>()) {
            case 822508846:
               return;
            default:
               throw null;
         }
      } else {
         BlockEntity var2 = this.l.level.getBlockEntity(var1);
         if (!(var2 instanceof Container)) {
            switch ((int)com.yiyiaddon.m.b.a<"s2sotw9cbcb6ec","I2CHP4Ic1jtX0BeCEyuBsTTXxdsUr5iJVnb7x9pX2w0=",-6112319036702035200,-6299578715354101089,-5258079855183970962,6952631495345088474>()) {
               case -1822503723:
                  this.A(
                     (String)com.yiyiaddon.m.b.a<"s313x2q0g61p17","jpH0hubdumuKM5Ou4f+e6AOKCV/WAzQGwl2R/Itf31dKMiyjdzuCyyPmTo9c6MYgTn5XB5GZkyz0rIt8+BimXf2/T1ICkBjOm0bvRZPKJtDt6uNegsYnRGz0ydoBZg==",-5779283011561503910,3162297218506694848,-8598982175189664848,-1549581002697540230>()
                  );
                  return;
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.c.d.b var3 = com.yiyiaddon.e.c.d.b.a(var1);
            if (var3 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"slev1pum0x825","Czg8l1XWnLhUhVlZgC+TlNtdw7eNhsmFAE03rCplvjA=",-7138041668299936373,-7364218058926903357,3898115202864395079,-3667792062952464329>()) {
                  case 480196043:
                     this.A(
                        (String)com.yiyiaddon.m.b.a<"s2pc79jv7bxcc2","ZPFsLOHmcJ/BIKIb7E97b5VX26rQJfYIH/uhOYL4YuzFikK8Q/8aW04SMLWcUeFs",7092457923220790793,-1893871950641440492,499184249252713242,-2152208734041305547>()
                     );
                     return;
                  default:
                     throw null;
               }
            } else {
               g var4 = this.a;
               this.a.a(var4, var3);
               com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"sdj6vw1ldppht","ucSZYNvUKS+jiPTCPxn479UXPLBda6QDFnln/6POROM6VPDA",1412308265105651441,-164761888105255434,7534787039347284061,3478138056022017281>(),
                     var4.af() + ""
                  )
                  .a(var1.getX(), var1.getY(), var1.getZ())
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s1elmbrrvdlfvr","y6UrN6ZborZa+lcmYBKeY+FRry9WV9eql/u2ZCEjPrI=",-886122236396810650,-2038521408180707391,-6193091673680316214,-3567035929179275098>(),
                     var3.a().identifier().toString()
                  )
                  .a(
                     com.yiyiaddon.d.d.a.SUCCESS,
                     (String)com.yiyiaddon.m.b.a<"s3jp3jjxnfy034","wKL711AhjE2akW3oTRNaXbP3F6YY/jA008hbduhmNG7DBw==",8825686037989672555,-5755051258471916664,-3603926824538707953,-806275200224084964>()
                  )
                  .g();
               this.d(true);
            }
         }
      }
   }

   private void A(String var1) {
      com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"sdj6vw1ldppht","ucSZYNvUKS+jiPTCPxn479UXPLBda6QDFnln/6POROM6VPDA",1412308265105651441,-164761888105255434,7534787039347284061,3478138056022017281>(),
            (String)com.yiyiaddon.m.b.a<"sgex4m8irh7zo","oUvAMKPhKKmbWorlpQDDMX/lF9yXcv+t765Xp2MdAwvBLCXN",-6272869486741441261,-1987678213204853734,6999446528158489708,-5840555926509412749>()
         )
         .b(
            (String)com.yiyiaddon.m.b.a<"s3r9pir4gkwfmb","cVqgq4oKbZ2VS1Kpoxr+EMQL0f3DZhN7VVwxvcn9cnc=",-2383162038428032950,704760093567350531,1814361544407401636,9149141045198567840>(),
            var1
         )
         .a(
            com.yiyiaddon.d.d.a.FAILURE,
            (String)com.yiyiaddon.m.b.a<"scwru3qgvoxd9","i1KNw7bNkJDZUE0uQwNXgTvslc2r4qScXo+GjoGzs9R5NA==",2236639692234487383,-8615201943569540868,7585311607200564315,6610856316167262311>()
         )
         .g();
   }

   private boolean a(Player var1, InteractionHand var2) {
      if (this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"sjxgbx3mfadm5","/fETgrX1frli0UQMX8sg7GgOmtaUwWHQq38x8z7BW6g=",-6039424508741578457,-3009922155992077154,-3142701031965492084,8386163151883004221>()) {
            case -180411133:
               if (var2 == InteractionHand.MAIN_HAND) {
                  switch ((int)com.yiyiaddon.m.b.a<"ssqyjjyim1c52","PVJnfZy8DTN1VPNwQB+xiR/k9yxnnWrU81mdJZ2Y7dQ=",5506781814813445568,5517266060854432179,1636248112052918869,3206278362188473372>()) {
                     case 1912045962:
                        if (var1 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"se7hddb7u1sdj","GFMiTc5drBtP9b+QBtWIegsllSHrg77nu5ZTItHvZ1Q=",5733382969204725510,-4764380386022678546,7059893931524423974,5419326397020766141>()) {
                              case 2134713520:
                                 if (this.l.screen == null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1kgw7ih0lw0o7","EeW17zhIE8PHoLK4WEBHuSXPcM8Z+NVvSC9swiQcCss=",-4442182181181047631,-7023699186649233619,968827767808393848,-8189916397327447835>()) {
                                       case 276863479:
                                          switch ((int)com.yiyiaddon.m.b.a<"sub1bagoztn40","T0WYsQjDDVxfxM4kAvshFEjiHAsOkBMMdgg6CC/EdvY=",-8093707171143134629,-3625708444340251573,4446271228223100287,-3438890701187062018>()) {
                                             case -2011348771:
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

      switch ((int)com.yiyiaddon.m.b.a<"sm4xor4v0ds99","XMNTsVxChiiZds++cJzLulT2rVclMxrWEi5Hqm2vfhk=",5274987279851030020,7593449812934304765,7794950816235064374,-730738206038594085>()) {
         case -1085634041:
            return false;
         default:
            throw null;
      }
   }

   private BlockPos b() {
      HitResult var2 = this.l.hitResult;
      if (var2 instanceof BlockHitResult) {
         switch ((int)com.yiyiaddon.m.b.a<"sql5866kmyzna","joTh5/nZX+l1tpU5+vH0bb8oHJB4T/ZDuIhAngw/HBs=",-7973507844799324803,969014943987900603,6680343036915830438,144295583215652339>()) {
            case -1883966565:
               BlockHitResult var1 = (BlockHitResult)var2;
               BlockPos var10000 = var1.getBlockPos();
               switch ((int)com.yiyiaddon.m.b.a<"s3h7128o098113","1V+KCqvEFABa9hJEiJrfO2ryLuLI/2JCPUBij7leAbk=",4733634800991845927,8438201368474789606,4481556579970883742,-2749405390018728151>()) {
                  case 535671:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1s0n3nnfs6jry","mmD/S4z+ShmNjU4WGH5cQa+PWp8Z2awu4IN7Pe67dcY=",-8334442180339101276,450156691681468881,-4473491897705127888,-6910909685674254090>()) {
            case 1381660496:
               return null;
            default:
               throw null;
         }
      }
   }

   private void render(f var1) {
      if (this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"sway2xn3ii04x","Jl4SOiO/x7oAeqSyTimiilVtZxTmqRVsI8Ys5VNPOsg=",6503030504617917199,-4490905564013541309,8507772879971434669,2892229183631523506>()) {
            case 1686828455:
               if (this.l.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ccr7zsahg3op","quNHkmS2oaof9MeSJkbNQRG4bBNQgUgwUlzMYYEkQPk=",-4303450908479542637,-725362798246819246,5895526717217429668,-1774382150118946098>()) {
                     case 940042700:
                        if (this.l.level != null) {
                           BlockPos var2 = this.b();
                           if (this.a != g.START) {
                              label61:
                              switch ((int)com.yiyiaddon.m.b.a<"s1phyv5b8ijzjr","VWHaMaf6pAPwgoXsgbZ984DuqButiQZOLxVLmncEPto=",6548812863762700144,-6500422084471707550,4712309268790359987,7322237229209808384>()) {
                                 case -456271001:
                                    if (this.a != g.END) {
                                       if (var2 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1rlksg0e2oxyd","S8ZbqzLlUhfoR4fFRKayCNwkG87t/X0TSYa3riusR0U=",-5325407210021350187,1787016617885659962,3975531227736022578,1726168818318552410>()) {
                                             case -1636714480:
                                                var1.a(var2.getX(), var2.getY(), var2.getZ(), l, l, com.yiyiaddon.l.g.a.j.Both, 1.5F);
                                                switch ((int)com.yiyiaddon.m.b.a<"s2ymy2riuxgv8b","G84U6sVI4DgCD2t0I8QQ/WdhYsHUnd4wlXvBnoWG3R4=",-8664039055441512967,6519559796108844519,8075770455240336443,-6116477772104110909>()) {
                                                   case -1419199675:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       return;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1mkzq8ri5c7gu","ccc511B79bAsCl+2xc7efB2WlFYwxARE2VFXiOMr0Ck=",7675765199143418426,4949712007547073728,-4184192058509273674,5063043326221759469>()) {
                                       case -1643066017:
                                          break label61;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.f == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2lutcv3etafte","U6zqNdmBy+GxTp60iKtyR7vzBj0QNjxdUv8oTWc+8ns=",311903194955472151,5698433415315581868,-1608159480894382873,1063536434560623971>()) {
                                 case -1621346628:
                                    if (var2 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s549qgfm67bqy","Kv0Lawnz74Vx2OiWyyy4TEtDE8is7X7vr5lGv7vg/Aw=",-6081020692777102075,1964399473917584834,-3750433306785120536,-537523754510053041>()) {
                                          case 1828218324:
                                             var1.a(var2.getX(), var2.getY(), var2.getZ(), i, i, com.yiyiaddon.l.g.a.j.Lines, 1.5F);
                                             switch ((int)com.yiyiaddon.m.b.a<"s2i6rguay1cg1l","uO8N3IRVQjQ82wqBN7hBjFPG7Qg1Aa6Dcw7JYqk6VQE=",-4433423430803787304,-2905421427101657403,2746518420050305346,5409353579009733144>()) {
                                                case 787697277:
                                                   return;
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

                           var1.a(this.f.getX(), this.f.getY(), this.f.getZ(), i, i, com.yiyiaddon.l.g.a.j.Lines, 1.5F);
                           if (var2 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1a9v50gpmvcye","9QTlWIogE8JrxJoVzQRZhAx8KpogHKWw4Ij9Kc/BAxg=",7439977723146545821,9127839024677095998,2253943585104213394,-9161139614496304213>()) {
                                 case 674629754:
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           var1.a(a(this.f, var2), j, k, com.yiyiaddon.l.g.a.j.Lines, 1.5F);
                           int var3 = Math.abs(this.f.getX() - var2.getX()) + 1;
                           int var4 = Math.abs(this.f.getZ() - var2.getZ()) + 1;
                           float var5 = (Math.min(this.f.getX(), var2.getX()) + Math.max(this.f.getX(), var2.getX())) / 2.0F + 0.5F;
                           float var6 = (Math.min(this.f.getZ(), var2.getZ()) + Math.max(this.f.getZ(), var2.getZ())) / 2.0F + 0.5F;
                           var1.a("" + var3 + var4, var5, Math.min(this.f.getY(), var2.getY()) + 1.6, var6, 10.0F, k, 1.0F, true);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s8akk3ycvrsz3","DJY7vlC7rW18AR2mihvu4qOAMkmRXsa6D3dbA+tJdz4=",-691298451349715550,-6235094050774700151,-8757866187231400475,-7971220510075798941>()) {
                           case -1796797740:
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
   }

   private static AABB a(BlockPos var0, BlockPos var1) {
      int var2 = Math.min(var0.getX(), var1.getX());
      int var3 = Math.max(var0.getX(), var1.getX());
      int var4 = Math.min(var0.getY(), var1.getY());
      int var5 = Math.min(var0.getZ(), var1.getZ());
      int var6 = Math.max(var0.getZ(), var1.getZ());
      return new AABB(var2, var4, var5, var3 + 1.0, var4 + 1.0, var6 + 1.0);
   }
}
