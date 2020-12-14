#-------------------------------------------------------------------------------
# Name:        module1
# Purpose:
#
# Author:      Emilie Veronika
#
# Created:     27/11/2016
# Copyright:   (c) Emilie 2016
# Licence:     <your licence>
#------------------------------------------------------------------------------

#La fonction affiche_menu servira a la fin du codage pour demander a l'utilisateur s'il veut recommencer ou quitter.
def affiche_menu():
    print("Menu :")
    print("1 - Recommencer les etapes de chiffrage")
    print("2 - Nous quitter pour utiliser votre code secret (a bon escient evidemment !)")
    choix=input("Choix: ")

    if choix==2:
        continu=False
    else:
        continu=True

    return continu    # Renvoie le choix de l'utilisateur

#La fonction code_decode permet de coder ou d?coder un mot, les variables utiliser sont nb(le nombre de lettre du mot)
#deca(le decalage choisi) et listmot(les lettres de l'aphabet). Pour chaque lettre du mot, le programme cherche le rang dans alph, ensuite ce rang
#est additione avec le decalage le tout modulo 26 pour que l'on reste dans l'aphabet meme si le decalage ajoute au rang depasse le nombre de lettre de
#l'alphabet et qu'ainsi l'alphabet forme une boucle infini ensuite on change la lettre de listmot avec la lettre correspondant au nouveau rang.

def code_decode():
    for i in range(nb):
            if listmot[i]!=" ":
                rang=alph.index(listmot[i])
                rang=(rang+deca)%26
                listmot[i]=alph[rang]
    return listmot


#liste alph contenant les lettres de l'alphabet
alph=["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"]


continu=True


#(while)Tant que continu est vrai continuer le programme le redemarrer depuis le debut
while continu==True:

    #Demander le decalage a l'utilisateur
    print("Ave ! Le cryptage de Cesar va commencer suivez les etapes suivantes et vous pourrez chiffrer et dechiffrer tous les mots en un tour de main !")
    print(" ")#les prints affichant un espace servent ? aerer l'affichage

    print("Etape 1: saisissez un decalage qui n'est pas un multiple de 26 pour coder/decoder votre mot ")
    print("Les multiples de 26 n'ont aucun interet pour le chiffrage, il vous renvoie le mot que vous avez saisi")

    deca=input("Entrer votre decalage")



    #(while)Tant que le decalage est un multiple de 26 demander un autre decalage car sinon le mot ne sera pas modifie
    while deca%26==0:
        print(" ")
        print("Le multiple de 26 est exclus du chiffrage ne le rentrez pas !")
        print("Saisissez un autre decalage sinon le mot ne sera pas coder ou decoder ")
        print(" ")
        deca=input("Entrer votre decalage")


    #demander le mot a l'utilisateur et le transformer en liste et compter le nombre de lettre dans le mot
    print("Etape 2: Entrer le mot secret")
    mot=raw_input("mot secret")
    listmot=list(mot)
    nb=len(mot)


    #demander a l'utilisateur s'il veut coder ou decoder un mot
    print("Etape 3: preferez vous coder ou decoder ?")
    code=input("Pour coder tapez 1 et pour decoder 2")


    #si l'utilisateur choisi le deux le decalage est soustrait donc on multiplie par -1 le decalage.
    if code==2:
        deca=-deca


    #(if) si code est egal a 1 y prend la valeur 2 sinon la valeur 1
    #On attribue a y ces deux valeurs car si code prend la valeur 1 ou 2 y*code=2 sinon c'est different de 2 donc on redemande la valeur la code.
    if code==1 :
            y=2
    else :
            y=1

    #tant que y fois code est different de 2 redemander la valeur de code
    while code*y!=2:
            print("voulez vous coder ou decoder")
            code=input("coder=1 ou decoder=2")

            #(if) si  code est egal a 1 y prend la valeur 2 sinon la valeur 1
            if code==1 :
                y=2
            else:
                y=1

   #Code ou decode en utilisant la fonction creer precedement
    codedecode=code_decode()


    #Les differents caracteres de la liste sont mis les un a cote des autres pour former le mot code ou decode et afficher le mot
    mot="".join(listmot)
    print("Votre mot est devenu "+str(mot))
    print(" ")

    #demander a l'utilisateur s'il veut continuer si il tape un sinon il tape 2
    continu = affiche_menu()


print(" ")
print("Felicitation vous avez pu cacher vos messages secrets")
print("vale et tibi gratias ago :) ")

#Note : 18/20
# Votre programme fonctionne tres bien, mais comme je vous l'ai explique en classe, il y a un probleme dans votre gestion des fonctions
# qui peut generer de gros problemes.