package com.mycompany.organizationdemo.businesslayer.managers.interfaces;

import com.mycompany.organizationdemo.domain.dtos.FaqDto;
import java.util.Collection;

public interface IFaqManager {
  Collection<FaqDto> getAll();
}
