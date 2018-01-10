package com.leandro.guerreirosapp.Adapter.Alunos;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    boolean onItemDismiss(int position);
}
