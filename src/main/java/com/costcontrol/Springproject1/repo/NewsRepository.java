package com.costcontrol.Springproject1.repo;

import com.costcontrol.Springproject1.models.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {

}
