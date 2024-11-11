public class ArvoreAVL extends ArvoreDePesquisa {
    
    @Override
    protected void inOrder(No atual){
        if(hasLeft(atual)) inOrder(leftChild(atual));
        System.out.print(atual.element + "[" + atual.fb + "]    ");
        if(hasRight(atual)) inOrder(rightChild(atual));
    }

    private No rotacaoSimplesEsquerda(No no){
        No filho = rightChild(no);
        if(!isRoot(no)){
            No pai = parent(no);
            filho.parent = pai;
            if(leftChild(pai) == no) pai.lChild = filho;
            else pai.rChild = filho;
        }
        else{
            filho.parent = null;
            raiz = filho;
        }
        if(hasLeft(filho)) no.rChild = leftChild(filho);
        else no.rChild = null;
        filho.lChild = no;
        no.parent = filho;
        no.fb = (++no.fb) - Math.min(filho.fb, 0);
        filho.fb = (++filho.fb) + Math.max(no.fb, 0);
        return filho;
    }
    
    private No rotacaoSimplesDireita(No no){
        No filho = leftChild(no);
        if(!isRoot(no)){
            No pai = parent(no);
            filho.parent = pai;
            if(leftChild(pai) == no) pai.lChild = filho;
            else pai.rChild = filho;
        }
        else{
            filho.parent = null;
            raiz = filho;
        }
        if(hasRight(filho)) no.lChild = rightChild(filho);
        else no.lChild = null;
        filho.rChild = no;
        no.parent = filho;
        no.fb = (--no.fb) - Math.max(filho.fb, 0);
        filho.fb = (--filho.fb) + Math.min(no.fb, 0);
        return filho;
    }

    private No rotacaoDuplaEsquerda(No no){
        rotacaoSimplesDireita(rightChild(no));
        return rotacaoSimplesEsquerda(no);
    }

    private No rotacaoDuplaDireita(No no){
        rotacaoSimplesEsquerda(leftChild(no));
        return rotacaoSimplesDireita(no);
    }

    @Override
    public void insert(Object elem){
        No novo = new No(elem);
        if(isEmpty()) raiz = novo;
        else{
            No pai = find(elem, raiz);
            if(pai.element == elem) throw new BaseException("Elemento já existe na Árvore");
            else{
                if((int)elem < (int)pai.element) pai.lChild = novo;
                else pai.rChild = novo;
                novo.parent = pai;
            }
            No atual = novo;
            while(!isRoot(atual)){
                pai = parent(atual);
                if(leftChild(pai) == atual) pai.fb++;
                else if(rightChild(pai) == atual) pai.fb--;
                if(pai.fb == 0) break;
                if(pai.fb == 2){
                    if(atual.fb < 0) atual = rotacaoDuplaDireita(pai);
                    else atual = rotacaoSimplesDireita(pai);
                }
                else if(pai.fb == -2){
                    if(atual.fb > 0) atual = rotacaoDuplaEsquerda(pai);
                    else atual = rotacaoSimplesEsquerda(pai);
                }
                else atual = pai;
            }
        }
        ++n;
    }

    // @Override
    // public No remove(Object elem){
    //     if(isEmpty()) throw new BaseException("Árvore vazia, não há elementos");
    //     No no = find(elem, raiz);
    //     No retorno = no;
    //     No paiRemovido = null;
    //     if(no.element != elem) throw new BaseException("Elemento não existe na Árvore");
    //     boolean ehRaiz = isRoot(no);
    //     No pai = null;
    //     if(!ehRaiz){
    //         pai = parent(no);
    //         paiRemovido = pai;
    //     }
    //     if(isExternal(no)){
    //         if(ehRaiz) raiz = null;
    //         else{
    //             No esquerdo = leftChild(pai);
    //             if(esquerdo == no) pai.lChild = null;
    //             else pai.rChild = null;
    //         }
    //     }
    //     else if(hasLeft(no) && !hasRight(no) || !hasLeft(no) && hasRight(no)){
    //         if(hasLeft(no)){
    //             if(ehRaiz){
    //                 raiz = leftChild(no);
    //                 raiz.parent = null;
    //             }
    //             else{
    //                 No filhoEsquerdo = leftChild(no);
    //                 filhoEsquerdo.parent = pai;
    //                 pai.lChild = filhoEsquerdo;
    //             }
    //         }
    //         else{
    //             if(ehRaiz){
    //                 raiz = rightChild(no);
    //                 raiz.parent = null;
    //             }
    //             else{
    //                 No filhoDireito = rightChild(no);
    //                 filhoDireito.parent = pai;
    //                 pai.rChild = filhoDireito;
    //             }
    //         }
    //     }
    //     else{
    //         No substituto = rightChild(no);
    //         while(hasLeft(substituto)) substituto = leftChild(substituto);
    //         no.element = substituto.element;
    //         pai = parent(substituto);
    //         if(hasRight(substituto)){
    //             substituto = rightChild(substituto);
    //             substituto.parent = pai;
    //             pai.rChild = substituto;
    //         }
    //         else{
    //             if(substituto == leftChild(pai)) pai.lChild = null;
    //             else pai.rChild = null;
    //         }
    //     }
    //     --n;
    //     No atual = paiRemovido;
    //     while(!isRoot(atual)){
    //         if(leftChild(pai) == atual) pai.fb--;
    //         else if(rightChild(pai) == atual) pai.fb++;
    //         if(pai.fb == 2){
    //             if(atual.fb < 0) atual = rotacaoDuplaDireita(pai);
    //             else atual = rotacaoSimplesDireita(pai);
    //         }
    //         else if(pai.fb == -2){
    //             if(atual.fb > 0) atual = rotacaoDuplaEsquerda(pai);
    //             else atual = rotacaoSimplesEsquerda(pai);
    //         }
    //         if(pai.fb != 0) break;
    //     }
    //     return retorno;
    // }
}